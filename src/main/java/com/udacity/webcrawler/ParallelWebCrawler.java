package com.udacity.webcrawler;

import com.udacity.webcrawler.json.CrawlResult;
import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import javax.inject.Inject;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;


/**
 * A concrete implementation of {@link WebCrawler} that runs multiple threads on a
 * {@link ForkJoinPool} to fetch and process multiple web pages in parallel.
 */
final class ParallelWebCrawler implements WebCrawler {

    private final Clock clock;
    private final Duration timeout;
    private final int popularWordCount;
    private final ForkJoinPool pool;
    private final int maxDepth;
    private final List<Pattern> ignoredUrls;
    private PageParserFactory pageParserFactory;
    public static Map<String, Integer> counts;
    public static Set<String> visitedUrls;

    @Inject
    ParallelWebCrawler(
            Clock clock,
            @Timeout Duration timeout,
            @PopularWordCount int popularWordCount,
            @TargetParallelism int threadCount,
            @MaxDepth int maxDepth,
            @IgnoredUrls List<Pattern> ignoredUrls,
            PageParserFactory pageParserFactory
    ) {

        this.pageParserFactory = pageParserFactory;
        this.maxDepth = maxDepth;
        this.ignoredUrls = ignoredUrls;
        this.clock = clock;
        this.timeout = timeout;
        this.popularWordCount = popularWordCount;
        this.pool = new ForkJoinPool(Math.min(threadCount, getMaxParallelism()));
    }

    @Override
    public CrawlResult crawl(List<String> startingUrls) {

        Instant deadline = clock.instant().plus(timeout);
        counts = new ConcurrentHashMap<>();
        visitedUrls = ConcurrentHashMap.newKeySet();
        for (String url : startingUrls) {
            pool.invoke(new task(url, deadline, maxDepth));

        }
        if (counts.isEmpty()) {
            return new CrawlResult.Builder()
                    .setWordCounts(counts)
                    .setUrlsVisited(visitedUrls.size())
                    .build();
        }
        return new CrawlResult.Builder()
                .setWordCounts(WordCounts.sort(counts, popularWordCount))
                .setUrlsVisited(visitedUrls.size())
                .build();

    }

    @Override
    public int getMaxParallelism() {
        return Runtime.getRuntime().availableProcessors();
    }

    class task extends RecursiveAction {
        final String url;
        final Instant deadline;
        final int maxDepth;


        public task(String url, Instant deadline, int maxDepth) {
            this.url = url;
            this.deadline = deadline;
            this.maxDepth = maxDepth;
        }

        public String getUrl() {
            return url;
        }

        public Instant getDeadline() {
            return deadline;
        }

        public int getMaxDepth() {
            return maxDepth;
        }

        @Override
        protected void compute() {

            if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
                return;
            }
            for (Pattern pattern : ignoredUrls) {
                if (pattern.matcher(url).matches()) {
                    return;
                }
            }
            if (!visitedUrls.add(url)) {
                return;
            }
            PageParser.Result result = pageParserFactory.get(url).parse();

            for (Map.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
                counts.compute(e.getKey(), (k, v) -> (v == null) ? e.getValue() : e.getValue() + v);

                for (String link : result.getLinks()) {
                    invokeAll(new task(link, deadline, maxDepth - 1));

                }

            }

        }
    }
}









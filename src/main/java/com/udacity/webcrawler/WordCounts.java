package com.udacity.webcrawler;

import org.checkerframework.checker.units.qual.K;

import java.security.Key;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Utility class that sorts the map of word counts.
 *
 * <p>TODO: Reimplement the sort() method using only the Stream API and lambdas and/or method
 *          references.
 */

// inforce dependency injection and set class back to default insted of public as well as constructor and sort method
public final class WordCounts {

  /**
   * Given an unsorted map of word counts, returns a new map whose word counts are sorted according
   * to the provided {@link WordCountComparator}, and includes only the top
   * {@param popluarWordCount} words and counts.
   *
   * <p>TODO: Reimplement this method using only the Stream API and lambdas and/or method
   *          references.
   *
   * @param wordCounts       the unsorted map of word counts.
   * @param popularWordCount the number of popular words to include in the result map.
   * @return a map containing the top {@param popularWordCount} words and counts in the right order.
   */
  public static Map<String, Integer> sort(Map<String, Integer> wordCounts, int popularWordCount) {

    // TODO: Reimplement this method using only the Stream API and lambdas and/or method references.


    final PriorityQueue<Map.Entry<String, Integer>>  topCounts = new PriorityQueue<>(wordCounts.size(), new WordCountComparator());
//    wordCounts.entrySet().stream().sorted(new WordCountComparator()).forEach(s -> topCounts.put(s.getKey(), s.getValue()));
//    PriorityQueue<Map.Entry<String, Integer>> sortedCounts = new PriorityQueue<>(wordCounts.size(), new WordCountComparator());
//    sortedCounts.addAll(wordCounts.entrySet());
//
//    // after List is sorted we take words based on either popularWordCount OR wordCounts (Minimum)
//    System.out.println(sortedCounts+ "Count : "+popularWordCount);
//    for (int i = 0; i < Math.min(popularWordCount, wordCounts.size()); i++) {
//      Map.Entry<String, Integer> entry = sortedCounts.poll();
//      topCounts.put(entry.getKey(), entry.getValue());
//    }
    WordCountComparator comparator = new WordCountComparator();
    wordCounts.entrySet().stream().sorted(comparator).limit(Math.min(popularWordCount, wordCounts.size())).forEach(s -> topCounts.add(s));
    Map<String, Integer> topCountsResult = new LinkedHashMap<>();
    topCounts.stream().forEach((k)->topCountsResult.put(k.getKey(),k.getValue()));
    return topCountsResult;
  }

  /**
   * A {@link Comparator} that sorts word count pairs correctly:
   *
   * <p>
   * <ol>
   *   <li>First sorting by word count, ranking more frequent words higher.</li>
   *   <li>Then sorting by word length, ranking longer words higher.</li>
   *   <li>Finally, breaking ties using alphabetical order.</li>
   * </ol>
   */
  private static final class WordCountComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public  int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) {
      if (!a.getValue().equals(b.getValue())) {
        return b.getValue() - a.getValue();
      }
      if (a.getKey().length() != b.getKey().length()) {
        return b.getKey().length() - a.getKey().length();
      }
      return a.getKey().compareTo(b.getKey());
    }
  }

  public WordCounts() {
    // This class cannot be instantiated
  }
}
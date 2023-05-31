package com.udacity.webcrawler.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.udacity.webcrawler.PopularWordCount;
import com.udacity.webcrawler.WordCounts;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class to write a {@link CrawlResult} to file.
 */
public final class CrawlResultWriter {
  private final CrawlResult result;

  /**
   * Creates a new {@link CrawlResultWriter} that will write the given {@link CrawlResult}.
   */
  public CrawlResultWriter(CrawlResult result) {
    this.result = Objects.requireNonNull(result);
  }

  /**
   * Formats the {@link CrawlResult} as JSON and writes it to the given {@link Path}.
   *
   * <p>If a file already exists at the path, the existing file should not be deleted; new data
   * should be appended to it.
   *
   * @param path the file path where the crawl result data should be written.
   */
  public void write(Path path) {
    // This is here to get rid of the unused variable warning.
    Map<String,Integer> wordsCount = result.getWordCounts();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

    try(Writer writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE) ) {
      objectMapper.writeValue(writer,new output(wordsCount,result.getUrlsVisited()));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // TODO: Fill in this method.
  }

  /**
   * Formats the {@link CrawlResult} as JSON and writes it to the given {@link Writer}.
   *
   * @param writer the destination where the crawl result data should be written.
   */
  public void write(Writer writer) {
    // This is here to get rid of the unused variable warning.
    Map<String,Integer> wordsCount = result.getWordCounts();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

    try {
      objectMapper.writeValue(writer,new output(wordsCount,result.getUrlsVisited()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // TODO: Fill in this method.
  }


  class output implements Serializable{
    public Map<String,Integer> wordCounts;
    public  Integer urlsVisited;

    public output(Map<String, Integer> wordCounts, Integer urlsVisited) {

      this.wordCounts = wordCounts;
      this.urlsVisited = urlsVisited;
    }
  }
}

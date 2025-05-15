package com.revenera.gcs.utils;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringGenerator {


  private static final SecureRandom rnd = new SecureRandom();

  private String prefix = "";

  private Case force = Case.None;

  private final List<String> elements = new LinkedList<>();


  private StringGenerator() {
  }

  public String generate(final String charset, final int length) {

    return rnd.ints(length, 0, charset.length())
              .mapToObj(charset::charAt)
              .map(Object::toString)
              .collect(Collectors.joining());
  }

  public StringGenerator withCase(final Case value) {
    this.force = value;
    return this;
  }

  public StringGenerator withPrefix(final String value) {
    this.prefix = value;
    return this;
  }

  public StringGenerator withSeparator(final String separator) {
    this.elements.add(separator);
    return this;
  }

  public StringGenerator withElement(final String element) {
    this.elements.add(this.force == Case.Upper ? element.toUpperCase() :
                      this.force == Case.Lower ? element.toLowerCase() :
                      element);
    return this;
  }

  public StringGenerator withGuid() {
    return withElement(UUID.randomUUID().toString());
  }

  public StringGenerator withElement(final String charset, final int length) {
    this.elements.add(generate(charset, length));
    return this;
  }

  public StringGenerator withElement(final String separator, final String charset, final int length) {
    return withSeparator(separator).withElement(charset, length);
  }


  public StringGenerator withElements(final String separator, final String charset, final int length, final int count) {

    IntStream.range(0, count)
             .forEach(x -> withElement(separator, charset, length));

    return this;
  }

  public String build() {
    return this.prefix + String.join("", this.elements);
  }

  public static StringGenerator create() {
    return new StringGenerator();
  }
}

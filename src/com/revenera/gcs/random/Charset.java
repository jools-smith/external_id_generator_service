package com.revenera.gcs.random;

public class Charset {
  private final String charset;

  public static final String EMPTY = "";
  public static final String HYPHEN = "-";
  public static final String UNDERSCORE = "_";
  public static final String POINT = ".";
  
  public static final Charset numeric = create("0123456789");
  public static final Charset alpha = create("abcdefghijklmnopqrstuvwxyz");
  public static final Charset hex = create(numeric.charset + "abcdef");
  public static final Charset alpha_numeric = create(alpha.charset + numeric.charset);

  public static Charset alpha_numeric_safe = create(alpha_numeric.charset
                                                            .replace("0", "")
                                                            .replace("1", "")
                                                            .replace("o", "")
                                                            .replace("i", ""));

  private Charset(final String charset) {
    this.charset = charset;
  }

  public String charset() {
    return this.charset;
  }

  public Character charAt(final int index) {
    return this.charset.charAt(index);
  }

  public int length() {
    return this.charset.length();
  }

  public static Charset create(final String charset) {
    return new Charset(charset);
  }

}

package com.mycmd;

/** String helper functions for commands. */
public class StringUtils {
  public static boolean isEmpty(String s) {
    return s == null || s.isBlank();
  }

  public static String join(String[] arr, int start) {
    StringBuilder sb = new StringBuilder();
    for (int i = start; i < arr.length; i++) {
      if (i > start) sb.append(' ');
      sb.append(arr[i]);
    }
    return sb.toString();
  }
}

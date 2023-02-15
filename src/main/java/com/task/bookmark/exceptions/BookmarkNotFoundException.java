package com.task.bookmark.exceptions;

public class BookmarkNotFoundException extends RuntimeException {
  public BookmarkNotFoundException(String msg) {
    super(msg);
  }
}

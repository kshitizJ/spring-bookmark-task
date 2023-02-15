package com.task.bookmark.exceptions;

public class FolderNotFoundException extends RuntimeException {
  public FolderNotFoundException(String msg) {
    super(msg);
  }
}

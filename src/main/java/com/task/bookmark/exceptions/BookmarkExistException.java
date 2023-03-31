package com.task.bookmark.exceptions;

public class BookmarkExistException extends RuntimeException {
    public BookmarkExistException(String msg) {
        super(msg);
    }
}

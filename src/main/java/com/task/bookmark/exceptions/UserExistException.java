package com.task.bookmark.exceptions;

public class UserExistException extends RuntimeException{
    public UserExistException(String msg){
        super(msg);
    }
}

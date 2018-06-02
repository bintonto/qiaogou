package com.bintonto.Exception;

public class RepeatQiangGouException extends QiangGouException {

    public RepeatQiangGouException(String message) {
        super(message);
    }

    public RepeatQiangGouException(String message, Throwable cause) {
        super(message, cause);
    }
}
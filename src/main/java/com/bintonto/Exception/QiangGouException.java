package com.bintonto.Exception;

public class QiangGouException extends RuntimeException {
    public QiangGouException(String message) {
        super(message);
    }

    public QiangGouException(String message, Throwable cause) {
        super(message, cause);
    }
}

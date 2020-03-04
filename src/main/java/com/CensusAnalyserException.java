package com;

public class CensusAnalyserException extends Exception {
    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type=ExceptionType.valueOf(name);
    }

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,DELIMITER_PROBLEM
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

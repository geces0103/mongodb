package com.example.demo.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6268065944377734717L;

    private static final int CODE = 404;
    private static final String DOMAIN = "HTTP";
    private static final String MESSAGE = "Recurso não encontrado";

    private final int code;
    private final String domain;

    public NotFoundException() {
        this(CODE, DOMAIN, MESSAGE);
    }

    public NotFoundException(String message, Object... args) {
        this(CODE, DOMAIN, message, args);
    }

    public NotFoundException(int code, String domain, String message, Object... args) {
        super(String.format(message, args));
        this.code = code;
        this.domain = domain;
    }

    public NotFoundException(Throwable cause) {
        this(cause, CODE, DOMAIN, MESSAGE);
    }

    public NotFoundException(Throwable cause, String message, Object... args) {
        this(cause, CODE, DOMAIN, message, args);
    }

    public NotFoundException(Throwable cause, int code, String domain, String message, Object... args) {
        super(String.format(message, args), cause);
        this.code = code;
        this.domain = domain;
    }
}

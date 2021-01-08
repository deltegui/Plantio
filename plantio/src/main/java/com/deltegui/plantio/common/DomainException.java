package com.deltegui.plantio.common;

public class DomainException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final DomainError error;

    private DomainException(DomainError error) {
        super(error.getMessage());
        this.error = error;
    }

    public static DomainException fromError(DomainError error) {
        return new DomainException(error);
    }

    public DomainError getError() {
        return error;
    }
}

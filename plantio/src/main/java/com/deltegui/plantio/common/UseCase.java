package com.deltegui.plantio.common;

public interface UseCase<T, R> {
    R handle(T request) throws DomainException;
}

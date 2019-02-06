package ru.einster.moneytransfer;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;


@Produces
@Singleton
public class GenericExceptionHandler implements ExceptionHandler<GenericException, GenericResponse> {

    @Override
    public GenericResponse handle(HttpRequest request, GenericException exception) {
        return new GenericResponse(exception.getMessage());
    }
}

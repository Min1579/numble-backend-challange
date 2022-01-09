package me.min.karrotmarket.shared.exceoption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedException extends RuntimeException {
    public DuplicatedException(final String resource) {
        super(String.format("%s Not Found", resource));
    }
}

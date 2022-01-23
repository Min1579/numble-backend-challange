package me.min.karrotmarket.shared.exceoption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException(final String resource) {
        super(String.format("%s Not Found", resource));
    }
}

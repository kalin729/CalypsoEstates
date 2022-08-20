package com.kalinkrumov.calypsoestates.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PropertyNotFoundException extends RuntimeException{

    private final String propertySlug;

    public PropertyNotFoundException(String propertySlug) {
        this.propertySlug = propertySlug;
    }

    public String getPropertySlug() {
        return propertySlug;
    }
}

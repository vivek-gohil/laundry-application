package com.laundry.main.common.service;

import java.util.Optional;

import com.laundry.main.exception.BadRequestException;
import com.laundry.main.exception.ResourceNotFoundException;

public abstract class BaseService {

    protected void validateNotNull(Object value, String message) {
        if (value == null) {
            throw new BadRequestException(message);
        }
    }

    protected <T> T getOrThrow(Optional<T> optional, String message) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(message));
    }
}

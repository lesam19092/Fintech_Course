package com.example.edadil_microservice.utils;

import com.example.edadil_microservice.handler.exception.EmptyResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Slf4j
public class EntityUtils {

    public static <T extends Collection<?>> T requireNonEmptyCollection(T collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return collection;
        }
        log.error("Empty collection: {}", collection.getClass().getSimpleName());
        throw new EmptyResultException("Empty collection: " + collection.getClass().getSimpleName());
    }

}

package com.example.edadil_microservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class EntityUtils {

    public static <T extends Collection<?>> T requireNonEmptyCollection(T collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return collection;
        }
        log.error("Empty collection: {}", collection.getClass().getSimpleName());
        throw new NoSuchElementException("Empty collection: " + collection.getClass().getSimpleName());
    }

    public static <E> E requirePresentEntity(Optional<E> entity) {
        if (entity.isPresent()) {
            return entity.get();
        }
        log.error("Entity with id not found: {}", entity.getClass().getSimpleName());
        throw new NoSuchElementException("Entity with id not found: " + entity.getClass().getSimpleName());
    }
}

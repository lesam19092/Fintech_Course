package org.example.foodru_microservice.utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.example.foodru_microservice.handler.exception.EmptyResultException;

import java.util.Collection;
import java.util.Optional;

@Slf4j
public class EntityUtils {

    public static <T extends Collection<?>> T requireNonEmptyCollection(T collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return collection;
        }
        log.error("Empty collection: {}", collection.getClass().getSimpleName());
        throw new EmptyResultException("Empty collection: " + collection.getClass().getSimpleName());
    }

    public static <E> E requirePresentEntity(Optional<E> entity) {
        if (entity.isPresent()) {
            return entity.get();
        }
        log.error("Entity with id not found");
        throw new EntityNotFoundException("Entity with id not found: ");
    }
}

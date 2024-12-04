package org.example.foodru_microservice.utils;

import jakarta.persistence.EntityNotFoundException;
import org.example.foodru_microservice.handler.exception.EmptyResultException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntityUtilsTest {

    @Test
    void requireNonEmptyCollection() {
        Collection<String> nonEmptyCollection = Collections.singletonList("item");
        assertDoesNotThrow(() -> EntityUtils.requireNonEmptyCollection(nonEmptyCollection));
    }

    @Test
    void requireNonEmptyCollectionEmpty() {
        Collection<String> emptyCollection = Collections.emptyList();
        assertThrows(EmptyResultException.class, () -> EntityUtils.requireNonEmptyCollection(emptyCollection));
    }

    @Test
    void requirePresentEntity() {
        Optional<String> presentEntity = Optional.of("entity");
        assertDoesNotThrow(() -> EntityUtils.requirePresentEntity(presentEntity));
    }

    @Test
    void requireNonPresentEntity() {
        Optional<String> emptyEntity = Optional.empty();
        assertThrows(EntityNotFoundException.class, () -> EntityUtils.requirePresentEntity(emptyEntity));
    }
}
package com.example.edadil_microservice.utils;

import com.example.edadil_microservice.handler.exception.EmptyResultException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityUtilsTest {

    @Test
    void requireNonEmptyCollection_Positive() {
        Collection<String> nonEmptyCollection = List.of("item1", "item2");
        assertDoesNotThrow(() -> EntityUtils.requireNonEmptyCollection(nonEmptyCollection));
    }

    @Test
    void requireNonEmptyCollection_Negative() {
        Collection<String> emptyCollection = List.of();
        EmptyResultException exception = assertThrows(EmptyResultException.class, () -> EntityUtils.requireNonEmptyCollection(emptyCollection));
        assertEquals("Empty collection: ListN", exception.getMessage());    }
}
package com.study.microservices.studyapplication.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.setField;
import static org.springframework.util.ReflectionUtils.getField;

@NoArgsConstructor(access = PRIVATE)
@RequiredArgsConstructor(access = PRIVATE)
public final class EntityMergeService {

    private static EntityMergeService instance = new EntityMergeService();

    @Getter
    @Setter
    private static Boolean isCached = TRUE;
    private static Map<Object, EntityMergeService> cacheEntityMergeServices = new HashMap<>();

    @NonNull
    private Class classType;

    public static EntityMergeService getInstance(Class classType) {
        if (isCached) {
            Optional<Map.Entry<Object, EntityMergeService>> entityMergeService = getFromCache(classType);
            if (FALSE.equals(entityMergeService.isPresent())) {
                cacheEntityMergeServices.put(classType, new EntityMergeService(classType));
            }
            return cacheEntityMergeServices.get(classType);
        }

        return new EntityMergeService(classType);
    }

    public void mergeObject(Object objectActual, Map<String, Object> fields) {
        if (isEmpty(fields)) {
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        /* Not accept with the properties are annotated to ignore */
        objectMapper.configure(FAIL_ON_IGNORED_PROPERTIES, TRUE);
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, TRUE);

        try {
            Object objectConverted = objectMapper.convertValue(fields, classType);

            fields.forEach((fieldName, fieldValue) -> {
                Field field = findField(classType, fieldName);
                field.setAccessible(TRUE); /* for private fields*/
                setField(field, objectActual, getField(field, objectConverted));
            });
        } catch (IllegalArgumentException error) {
            throw error;
        }
    }

    private static Optional<Map.Entry<Object, EntityMergeService>> getFromCache(final Class classType) {
        return cacheEntityMergeServices.entrySet()
                .stream()
                .filter(cacheEntityMergeService -> cacheEntityMergeService.getKey().equals(classType))
                .findFirst();
    }
}

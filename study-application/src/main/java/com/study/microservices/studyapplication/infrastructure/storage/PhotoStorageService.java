package com.study.microservices.studyapplication.infrastructure.storage;

import com.study.microservices.studyapplication.infrastructure.storage.exception.StorageException;
import com.study.microservices.studyapplication.infrastructure.storage.model.Photo;
import com.study.microservices.studyapplication.infrastructure.storage.model.PhotoResponse;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public interface PhotoStorageService {

    void save(Photo photo);
    void save(List<Photo> photos);
    void delete(String fileName);
    PhotoResponse getPhoto(String fileName);
    PhotoResponse getPhotoByNameAndMediaType(String fileName, List<MediaType> mediaTypesAcceptedRequest) throws HttpMediaTypeNotAcceptableException;

    default String generateNewFileName(String fileName) {
        return String.join("_", UUID.randomUUID().toString(), fileName);
    }

    /**
     * Validate if the media type of a file was accept by the request
     * @param mediaTypePhotoFound from the file
     * @param mediaTypesAcceptedRequest passed on the request in the header accept
     * @throws HttpMediaTypeNotAcceptableException return a http code 406 not acceptable
     */
    default void validateExtension(MediaType mediaTypePhotoFound, List<MediaType> mediaTypesAcceptedRequest) throws HttpMediaTypeNotAcceptableException {
        if (isNull(mediaTypesAcceptedRequest) || isEmpty(mediaTypesAcceptedRequest) || isNull(mediaTypePhotoFound)) {
            return;
        }

        mediaTypesAcceptedRequest.stream()
                /* here we validate if the content type is the same of the request */
                .filter(mediaType -> mediaType.isCompatibleWith(mediaTypePhotoFound))
                .findFirst()
                .orElseThrow(() -> new HttpMediaTypeNotAcceptableException(mediaTypesAcceptedRequest));
    }

    /**
     * Based on a path try to find de content type, for example <b>image/png</b>
     * @param file The complete path of a file in the storage
     * @return Return a content type like image/jpeg
     * @exception StorageException If something wrong get catch the original exception and throw another
     * @see StorageException
     */
    default String getContentTypeFromFile(Path file) {
        try {
            return Files.probeContentType(file);
        } catch (IOException e) {
            String errorMessage = format("Error during find content type of a file name:", file.getFileName());
            throw new StorageException(errorMessage);
        }
    }
}

package com.study.microservices.studyapplication.infrastructure.storage;

import com.study.microservices.studyapplication.core.storage.StorageProperties;
import com.study.microservices.studyapplication.infrastructure.storage.exception.StorageException;
import com.study.microservices.studyapplication.infrastructure.storage.model.Photo;
import com.study.microservices.studyapplication.infrastructure.storage.model.PhotoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.util.CollectionUtils.isEmpty;

public class LocalPhotoStorageServiceImpl implements PhotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void save(Photo photo) {
        try {
            /* here that we create a file in the local storage, necessary to pass the location plus the name o the file */
            /* ex.: /home/rodrigo/Pictures/study */
            FileCopyUtils.copy(photo.getInputStream(),
                    Files.newOutputStream(getPathWithNameFile(generateNewFileName(photo.getFileName()))));
        } catch (IOException e) {
            throw new StorageException("Error during storage the file.", e);
        }
    }

    @Override
    public void save(List<Photo> photos) {
        if (isNull(photos) || isEmpty(photos)) {
            return;
        }

        photos.stream().forEach(this::save);
    }

    @Override
    public void delete(String fileName) {
        if (isEmpty(fileName)) {
            return;
        }

        try {
            Files.deleteIfExists(getPathWithNameFile(fileName));
        } catch (IOException e) {
            String errorMessage = format("Error during deleting a file of name: %s", fileName);
            throw new StorageException(errorMessage);
        }
    }

    @Override
    public PhotoResponse getPhoto(String fileName) {
        if (isEmpty(fileName)) {
            return null;
        }

        try {
            return new PhotoResponse(Files.newInputStream(getPathWithNameFile(fileName)));
        } catch (IOException e) {
            String errorMessage = format("Error during searching a file of name: %s", fileName);
            throw new StorageException(errorMessage);
        }
    }

    @Override
    public PhotoResponse getPhotoByNameAndMediaType(String fileName, List<MediaType> mediaTypesAcceptedRequest) throws HttpMediaTypeNotAcceptableException {
        Path path = getPathWithNameFile(fileName);
        MediaType mediaTypePhoto = MediaType.parseMediaType(getContentTypeFromFile(path));
        validateExtension(mediaTypePhoto, mediaTypesAcceptedRequest);
        return getPhoto(fileName);
    }

    private Path getPathWithNameFile(String fileName) {
        /* Based on the location found in the config server plus the name of the file */
        return storageProperties.getLocal().getPhotos().resolve(Path.of(fileName));
    }
}

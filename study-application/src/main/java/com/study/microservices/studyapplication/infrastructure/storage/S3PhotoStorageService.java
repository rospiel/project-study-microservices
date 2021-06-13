package com.study.microservices.studyapplication.infrastructure.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.study.microservices.studyapplication.core.storage.StorageProperties;
import com.study.microservices.studyapplication.infrastructure.storage.exception.StorageException;
import com.study.microservices.studyapplication.infrastructure.storage.model.Photo;
import com.study.microservices.studyapplication.infrastructure.storage.model.PhotoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.net.URL;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.util.CollectionUtils.isEmpty;

public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void save(Photo photo) {
        String pathWithNameFile = getPathWithNameFile(generateNewFileName(photo.getFileName()));
        try {
            /*Info about the file*/
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(photo.getContentType());
            objectMetadata.setContentLength(photo.getSize());
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    pathWithNameFile,
                    photo.getInputStream(),
                    objectMetadata)
                    /* Allow anyone to access */
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            String errorMessage = format("Error during storage the file of name: %s on S3 Amazon.", pathWithNameFile);
            throw new StorageException(errorMessage);
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
        if (isBlank(fileName)) {
            return;
        }

        String pathWithNameFile = getPathWithNameFile(fileName);

        try {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    pathWithNameFile);
            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            String errorMessage = format("Error during removing the file of name: %s on S3 Amazon.", pathWithNameFile);
            throw new StorageException(errorMessage);
        }
    }

    @Override
    public PhotoResponse getPhoto(String fileName) {
        if (isBlank(fileName)) {
            return new PhotoResponse();
        }

        String pathWithNameFile = getPathWithNameFile(fileName);
        try {
            URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), pathWithNameFile);
            return new PhotoResponse(url.toString());
        } catch (Exception e) {
            String errorMessage = format("Error during providing a url access of the file of name: %s on S3 Amazon.", pathWithNameFile);
            throw new StorageException(errorMessage);
        }
    }

    @Override
    public PhotoResponse getPhotoByNameAndMediaType(String fileName, List<MediaType> mediaTypesAcceptedRequest) throws HttpMediaTypeNotAcceptableException {
        return null;
    }

    private String getPathWithNameFile(String fileName) {
        /* Based on the location found in the config server plus the name of the file */
        return format("%s/%s", storageProperties.getS3().getDirectory(), fileName);
    }
}

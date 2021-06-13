package com.study.microservices.studyapplication.api.controller;

import com.study.microservices.studyapplication.api.controller.model.request.restaurant.DeletePhotoProductRequest;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.GetPhotoProductRequest;
import com.study.microservices.studyapplication.api.controller.model.request.restaurant.PhotoProductRequest;
import com.study.microservices.studyapplication.infrastructure.storage.PhotoStorageService;
import com.study.microservices.studyapplication.infrastructure.storage.exception.StorageException;
import com.study.microservices.studyapplication.infrastructure.storage.model.Photo;
import com.study.microservices.studyapplication.infrastructure.storage.model.PhotoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.EMPTY_LIST;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequestMapping("/restaurant/{restaurantId}/product/{productId}/photos")
@RequiredArgsConstructor
public class RestaurantProductPhotoController {

    private final PhotoStorageService photoStorageService;

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void insertPhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                            @Valid PhotoProductRequest request) {
        photoStorageService.save(buildListPhoto(request.getFiles()));
    }

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void deletePhoto(@RequestBody @Valid DeletePhotoProductRequest request) {
        photoStorageService.delete(request.getFileName());
    }

    @GetMapping(consumes = APPLICATION_JSON_VALUE, produces = IMAGE_PNG_VALUE)
    public ResponseEntity<?> getPhotoPng(@RequestBody @Valid GetPhotoProductRequest request) {
        try {
            PhotoResponse photo = photoStorageService.getPhoto(request.getFileName());

            return buildResponse(photo);
        /* this is necessary when we don't find any file, the consumer will inform that accept an extension */
        /* like image/png but will send a json when throw an exception, the client will understand like a 406 Not Acceptable */
        } catch (StorageException error) {
            return ResponseEntity.notFound().build();
        }
    }

    /* In this example we don't informed the produces because we'll validate with the @RequestHeader, if <> from the content type of a file throw http 406  */
    @GetMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPhoto(@RequestBody @Valid GetPhotoProductRequest request,
                                                        @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

        try {
            PhotoResponse photo = photoStorageService.getPhotoByNameAndMediaType(request.getFileName(), MediaType.parseMediaTypes(acceptHeader));

            return buildResponse(photo);
            /* this is necessary when we don't find any file, the consumer will inform that accept an extension */
            /* like image/png but will send a json when throw an exception, the client will understand like a 406 Not Acceptable */
        } catch (StorageException error) {
            return ResponseEntity.notFound().build();
        }
    }

    private List<Photo> buildListPhoto(List<MultipartFile> files) {
        if (isNull(files) || isEmpty(files)) {
            return EMPTY_LIST;
        }

        return files.stream().map(file -> {
            try {
                return new Photo(file.getOriginalFilename(), file.getContentType(), file.getInputStream(), file.getSize());
            } catch (IOException e) {
                String errorMessage = format("Error during process file from request, file name: %s", file.getOriginalFilename());
                throw new StorageException(errorMessage.toString());
            }
        }).collect(toList());
    }

    private void validatePhotoSize(String valueMax, MultipartFile file) {
        DataSize size = DataSize.parse(valueMax);
        if (nonNull(file) && file.getSize() <= size.toBytes()) {
            System.out.println("Exceed");
        }
    }

    private ResponseEntity<?> buildResponse(PhotoResponse response) {
        if (isNull(response)) {
            return ResponseEntity.notFound().build();
        }

        return isNull(response.getUrl()) ?
                ResponseEntity.ok()
                        .contentType(IMAGE_PNG)
                        .body(new InputStreamResource(response.getInputStream())) :
                ResponseEntity.status(FOUND)
                        .header(LOCATION, response.getUrl())
                        .build();
    }
}

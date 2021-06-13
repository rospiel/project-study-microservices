package com.study.microservices.studyapplication.api.controller.model.request.restaurant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class DeletePhotoProductRequest {

    @NotBlank
    private String fileName;
}

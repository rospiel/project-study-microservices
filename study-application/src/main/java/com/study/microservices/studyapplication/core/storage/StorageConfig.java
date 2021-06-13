package com.study.microservices.studyapplication.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.study.microservices.studyapplication.infrastructure.storage.LocalPhotoStorageServiceImpl;
import com.study.microservices.studyapplication.infrastructure.storage.PhotoStorageService;
import com.study.microservices.studyapplication.infrastructure.storage.S3PhotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.study.microservices.studyapplication.core.storage.StorageProperties.StorageType.LOCAL;

@Configuration
@RequiredArgsConstructor
public class StorageConfig {

    private final StorageProperties storageProperties;

    @Bean
    /* To ensure that will create just if the property was to s3 */
    @ConditionalOnProperty(name = "studyapplication.storage.storageType", havingValue = "s3")
    public AmazonS3 amazonS3() {
        BasicAWSCredentials basicAWSCredentials =
                new BasicAWSCredentials("AKIA6Q2TLUJ3N2OVSGVS", "H12qcTmnv31w7idoKgrCQ6/HowuboS0jPihoVJz7");

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build();
    }

    /**
     * Based on storageType informed in the yml file produces a local or s3 storage
     * @return A interface based on a choice imputed in the yml file
     */
    @Bean
    public PhotoStorageService photoStorageService() {
        return LOCAL.equals(storageProperties.getStorageType()) ?
                new LocalPhotoStorageServiceImpl() :
                new S3PhotoStorageService();
    }
}

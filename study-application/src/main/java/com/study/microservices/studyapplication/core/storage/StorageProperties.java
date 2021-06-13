package com.study.microservices.studyapplication.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

import static com.study.microservices.studyapplication.core.storage.StorageProperties.StorageType.LOCAL;

@Getter
@Setter
@Component
@ConfigurationProperties("studyapplication.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private StorageType storageType = LOCAL;

    public enum StorageType {
        LOCAL, S3
    }

    @Getter
    @Setter
    public class Local {
        private Path photos;
    }

    @Getter
    @Setter
    public class S3 {
        private String accessKey;
        private String accessSecret;
        private String bucket;
        private String region;
        private String directory;
    }
}

package com.Qlog.backend.service.cloud;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FileStorageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadProfileImage(MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString();
            String ext = extracExt(file.getOriginalFilename());

            String fileName = uuid + "." + ext;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket + "/user_profile", fileName, file.getInputStream(), metadata);

            log.info("uploadFile: " + fileName);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] downloadProfileImage(String fileName) {
        try {
            System.out.println(bucket + "/user_profile");
            System.out.println("user_profile/" + fileName);
            S3ObjectInputStream objectContent = amazonS3Client.getObject(bucket + "/user_profile", fileName).getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String extracExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}

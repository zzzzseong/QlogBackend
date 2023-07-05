package com.Qlog.backend.service.cloud;

import com.Qlog.backend.domain.User;
import com.Qlog.backend.repository.UserRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FileStorageService {

    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public String uploadProfileImage(Long userId, MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString();
            String ext = extracExt(file.getOriginalFilename());

            String fileName = uuid + "." + ext;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket + "/user_profile", fileName, file.getInputStream(), metadata);

            User findUser = userRepository.findById(userId).get();
            findUser.updateProfileImage(fileName);

            log.info("uploadFile: " + fileName);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeProfileImage(String fileName) {
        amazonS3Client.deleteObject(bucket + "/user_profile", fileName);
    }


    public String extracExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}

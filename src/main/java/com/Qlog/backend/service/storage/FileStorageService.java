package com.Qlog.backend.service.storage;

import com.Qlog.backend.consts.ServiceConst;
import com.Qlog.backend.domain.User;
import com.Qlog.backend.service.UserService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FileStorageService {

    private final AmazonS3Client amazonS3Client;
    private final UserService userService;

    @Value("${cloud.aws.s3.bucket}") private String bucket;
    @Value("${S3-BUCKET-PROFILE-IMAGE-URL}") private String bucketUrl;

    @Transactional
    public String uploadProfileImage(User user, MultipartFile file) {
        if(!user.getProfileImageName().equals(ServiceConst.defaultProfileImage)) {
            this.removeProfileImage(user);
        }

        try {
            String uuid = UUID.randomUUID().toString();
            String ext = extracExt(file.getOriginalFilename());

            String fileName = uuid + "." + ext;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket + "/user_profile", fileName, file.getInputStream(), metadata);

            user.updateProfileImage(fileName);
            log.info("uploadFile: " + fileName);

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String removeProfileImage(User user) {
        if(user.getProfileImageName().equals(ServiceConst.defaultProfileImage)) return "기본 이미지는 제거할 수 없습니다.";

        amazonS3Client.deleteObject(bucket + "/user_profile", user.getProfileImageName());
        userService.updateProfileImagePath(user, ServiceConst.defaultProfileImage);

        return "이미지 삭제가 완료되었습니다.";
    }


    public String extracExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public String getProfileImageURL(String fileName) {
        return bucketUrl + fileName;
    }
}

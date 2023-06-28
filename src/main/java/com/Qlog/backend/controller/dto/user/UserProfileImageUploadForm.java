package com.Qlog.backend.controller.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class UserProfileImageUploadForm {
    private MultipartFile image;
}

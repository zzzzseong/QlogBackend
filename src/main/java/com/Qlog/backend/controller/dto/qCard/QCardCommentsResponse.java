package com.Qlog.backend.controller.dto.qCard;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QCardCommentsResponse {
    private String profileImgPath;
    private String username;
    private String comment;

    public QCardCommentsResponse(String profileImgPath, String username, String comment) {
        this.profileImgPath = profileImgPath;
        this.username = username;
        this.comment = comment;
    }
}

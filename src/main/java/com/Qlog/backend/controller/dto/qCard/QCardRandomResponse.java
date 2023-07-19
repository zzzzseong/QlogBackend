package com.Qlog.backend.controller.dto.qCard;

import com.Qlog.backend.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QCardRandomResponse {
    private Long id;
    private String image;
    private String username;
    private String question;

    public QCardRandomResponse(Long id, String image, String username, String question) {
        this.id = id;
        this.image = image;
        this.username = username;
        this.question = question;
    }
}

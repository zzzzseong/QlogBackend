package com.Qlog.backend.controller.dto.qCard;

import com.Qlog.backend.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QCardRandomResponse {
    private String image;
    private String username;
    private String question;
    private List<Comment> comments;

    public QCardRandomResponse(String image, String username, String question, List<Comment> comments) {
        this.image = image;
        this.username = username;
        this.question = question;
        this.comments = comments;
    }
}

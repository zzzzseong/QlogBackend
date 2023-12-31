package com.Qlog.backend.controller.dto.qCard;

import com.Qlog.backend.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QCardResponse {
    private String username;
    private String question;
    private List<Comment> comments;

    public QCardResponse(String username, String question, List<Comment> comments) {
        this.username = username;
        this.question = question;
        this.comments = comments;
    }
}
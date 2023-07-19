package com.Qlog.backend.controller.dto.comment;

import com.Qlog.backend.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentsResponse {
    private Long commentId;
    private String profileImgPath;
    private String username;
    private String comment;

    public CommentsResponse(Comment comment) {
        this.commentId = comment.getId();
    }
}

package com.Qlog.backend.controller.dto.qCard;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QCardCommentsResponse {
    private Long commentId;
    private String profileImgPath;
    private String username;
    private String comment;
    private boolean solved;
    private boolean adopted;

    public QCardCommentsResponse(Long commentId, String profileImgPath, String username,
                                 String comment, boolean adopted, boolean solved) {
        this.commentId = commentId;
        this.profileImgPath = profileImgPath;
        this.username = username;
        this.comment = comment;
        this.adopted = adopted;
        this.solved = solved;
    }
}

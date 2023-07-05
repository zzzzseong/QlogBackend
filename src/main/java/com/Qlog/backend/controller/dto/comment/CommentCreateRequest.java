package com.Qlog.backend.controller.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCreateRequest {
    private String comment;
}

package com.Qlog.backend.controller.dto.user;

import com.Qlog.backend.domain.Comment;
import com.Qlog.backend.domain.QCard;
import com.Qlog.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserReadResponse {
    private String name;
    private String introduction;
    private int point;
    private String tier;
    private String imgPath;

    private List<QCardRead> qCards = new ArrayList<>();
    private List<CommentRead> comments = new ArrayList<>();

    public UserReadResponse(User user, String ProfileImageURL) {
        this.name = user.getName();
        this.introduction = user.getIntroduction();
        this.point = user.getPoint();
        this.tier = user.getTier();
        this.imgPath = ProfileImageURL;
        for (QCard qCard : user.getQCards()) {
            this.qCards.add(new QCardRead(qCard.getId(), name, qCard.getQuestion(), qCard.isSolved()));
        }
        for (Comment comment : user.getComments()) {
            this.comments.add(new CommentRead(comment.getId()));
        }
    }
}

@Data
@AllArgsConstructor
class QCardRead {
    private Long id;
    private String name;
    private String question;
    private boolean solved;
}

@Data
@AllArgsConstructor
class CommentRead {
    private Long id;
}

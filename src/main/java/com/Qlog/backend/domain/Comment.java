package com.Qlog.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment {
    public Comment(User user, QCard qCard, String comment) {
        this.comment_user = user;
        this.comment_qCard = qCard;
        this.comment = comment;
    }

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User comment_user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qCard_id")
    private QCard comment_qCard;

    private boolean adopted = false;
    private String comment;

    public void update(String comment) {
        this.comment = comment;
    }
}
package com.Qlog.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment {

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
}
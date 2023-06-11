package com.Qlog.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class QCard {

    @Id @GeneratedValue
    @Column(name = "qCard_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User qCard_user;

    @OneToMany(mappedBy = "comment_qCard", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private boolean solved = false;
    private String question;
    private int point;
}
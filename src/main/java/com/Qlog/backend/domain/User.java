package com.Qlog.backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * follower and following은 어떻게 해결하지?
 * */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "qCard_user", cascade = CascadeType.ALL)
    private List<QCard> qCards = new ArrayList<>();

    @OneToMany(mappedBy = "comment_user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private String loginId;
    private String password;

    private String profileImage;
    private String name;
    private int point;
    private String tier;
}

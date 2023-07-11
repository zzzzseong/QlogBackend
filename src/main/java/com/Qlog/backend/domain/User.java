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

    public User(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.profileImageName = "default_profile_image.png";
        this.introduction = "안녕하세요. " + name + "입니다.";
        this.name = name;
        this.point = 0;
        this.tier = "None";
    }

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "qCard_user", cascade = CascadeType.ALL)
    private List<QCard> qCards = new ArrayList<>();

    @OneToMany(mappedBy = "comment_user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    private String loginId;
    private String password;

    private String profileImageName;
    private String name;
    private String introduction;
    private int point;
    private String tier;

    public void updatePoint(int point) {
        this.point += point;
    }
    public void updateProfileImage(String profileImageName) {
        this.profileImageName = profileImageName;
    }
    public void updateProfile(String name, String introduction) {
        this.name = name;
        this.introduction = introduction;
    }
}
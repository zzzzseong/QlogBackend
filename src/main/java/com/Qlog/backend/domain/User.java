package com.Qlog.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * follower and following은 어떻게 해결하지?
 * */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    public User(String loginId, String password, String name, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.profileImageName = "default_profile_image.png";
        this.introduction = "안녕하세요. " + name + "입니다.";
        this.name = name;
        this.point = 0;
        this.tier = "None";
        this.role = role;
    }

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "qCard_user", cascade = CascadeType.ALL)
    private List<QCard> qCards = new ArrayList<>();

    //QCard random 탐색 시 comment <-> user 간 순환참조 발생으로 인해 json ignore 추가
    @OneToMany(mappedBy = "comment_user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    private String loginId;
    private String password;

    private String profileImageName;
    private String name;
    private String introduction;
    private int point;
    private String tier;

    @Enumerated(EnumType.STRING)
    private Role role;

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


    //For Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
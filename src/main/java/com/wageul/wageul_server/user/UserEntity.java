package com.wageul.wageul_server.user;

import com.wageul.wageul_server.user.domain.User;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "name")
    private String name;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "language")
    private String language;

    @Column(name = "ageRange")
    private int ageRange;

    @Column(name = "introduce")
    private String introduce;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.email = user.getEmail();
        userEntity.profileImg = user.getProfileImg();
        userEntity.name = user.getName();
        userEntity.nationality = user.getNationality();
        userEntity.language = user.getLanguage();
        userEntity.ageRange = user.getAgeRange();
        userEntity.introduce = user.getIntroduce();
        return userEntity;
    }

    public User toModel() {
        return User.builder()
                .id(id)
                .email(email)
                .profileImg(profileImg)
                .name(name)
                .nationality(nationality)
                .language(language)
                .ageRange(ageRange)
                .introduce(introduce)
                .build();
    }
}

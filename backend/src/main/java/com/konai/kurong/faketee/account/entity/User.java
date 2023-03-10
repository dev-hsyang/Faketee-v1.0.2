package com.konai.kurong.faketee.account.entity;

import com.konai.kurong.faketee.account.dto.UserUpdateRequestDto;
import com.konai.kurong.faketee.account.util.Type;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseUserEntity;
import com.konai.kurong.faketee.account.util.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name="USR")
@Data
@Entity
public class User extends BaseUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PSWD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "EMAIL_AUTH_STATUS")
    private String emailAuthStatus;

    @Builder
    public User(Long id, String email, String password, String name, Role role, Type type, String emailAuthStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.type = type;
        this.emailAuthStatus = emailAuthStatus;
    }

    public void updatePassword(UserUpdateRequestDto requestDto) {

        this.password = requestDto.getNewPassword();
    }

    public void updateEmailAuthStatus() {

        this.emailAuthStatus = "T";
    }

    public void updateRole(Role role){

        this.role = role;
    }
}

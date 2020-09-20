package com.agency.pojo;

import lombok.*;

import java.io.*;
import java.util.Date;

/**
 * @author ch33orange
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

    private static final long serialVersionUID = 190253616680800788L;

    private Integer id;

    private String icon;

    private String username;

    private String password;

    private Integer gender;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    private String bio;

}
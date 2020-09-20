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
public class Teacher implements Serializable  {

    private static final long serialVersionUID = 6620151917887737149L;

    private Integer id;

    private String icon;

    private String username;

    private String password;

    private Integer gender;

    private String email;

    private String phone;

    private String degree;

    private String title;

    private String bio;

    private String question;

    private String answer;

    private Date createTime;

    private Date updateTime;


}
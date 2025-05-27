package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    private int mno;
    private String name;
    private String email;
    private String password;
    private String address;
    private String role;
    private double x, y;
    private double lat, lon;
    private String status;
    private String refresh;

    public Member(String name, String email, String password, String address) {
        this(0, name, email, password, address);
    }

    public Member(int mno, String name, String email, String password, String address) {
        this.mno = mno;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
    }

 



}

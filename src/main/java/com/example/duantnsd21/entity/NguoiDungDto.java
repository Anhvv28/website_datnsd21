package com.example.duantnsd21.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NguoiDungDto {
    private String email;
    private String hoTen;

    public NguoiDungDto(String email, String hoTen) {
        this.email = email;
        this.hoTen = hoTen;
    }
}

package com.ssafy.trip.model.dto;

import lombok.Data;

@Data
public class QuestionMailRequestDto {
    private String email;
    private String title;
    private String content;
}

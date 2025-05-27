package com.ssafy.trip.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Qna {
    private int no;                // QnA 번호 (Primary Key)
    private int mid;               // Member ID
    private String title;          // 제목
    private String content;        // 내용
    private LocalDateTime date;    // 등록일
}

package com.ssafy.trip.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchCondition {
    // 검색에 사용할 조건
    private String key;
    private String word;
    private int currentPage;
    private int itemsPerPage = 5;


    public SearchCondition(String key, String word, int currentPage) {
        this.key = key;
        this.word = word;
        this.currentPage = currentPage;

    }

    // offset 확인
    public int getOffset() {
        return (currentPage - 1) * itemsPerPage;
    }

    // key, word가 누락되지 않았는지 확인
    public boolean hasKeyword() {
        return key != null && !key.isBlank() && word != null && !word.isBlank();
    }
    

}

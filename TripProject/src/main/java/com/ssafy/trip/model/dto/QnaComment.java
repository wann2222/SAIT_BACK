package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaComment {
	private int cid;
	private int qna_id;
	private int mid;
	private String content;

}

package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostComment {
	private int cid;
	private int post_id;
	private int mid;
	private String content;
}

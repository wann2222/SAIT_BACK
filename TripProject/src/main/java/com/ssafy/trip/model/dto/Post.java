package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString

public class Post {
	private int no;
	private int mid;
	private String name;
	private @NonNull String title;
	private @NonNull String content;
	private @NonNull String date;
	
	public Post(int no, String title, String content, String date) {
	    this.no = no;
	    this.title = title;
	    this.content = content;
	    this.date = date;
	}
}

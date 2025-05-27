package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Attraction {
	private int ano; // 기본키
	private String name;
	private double lat;
	private double lon;
	private String address;
	private String overview;
	private String img;
	private int likes;

}

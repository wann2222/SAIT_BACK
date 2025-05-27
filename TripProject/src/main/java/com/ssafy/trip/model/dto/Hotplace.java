package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hotplace {
	private int hid;
	private int mid;
	private String title;
	private String date;
	private int contentType;
	private String image;
	private double x;
	private double y;

	
	public Hotplace(int mid, String title, String date, int contentType, String image) {
		super();
		this.mid = mid;
		this.title = title;
		this.date = date;
		this.contentType = contentType;
		this.image = image;
	}
	


	public Hotplace(int mid, String title, String date, String image, double x, double y) {
		super();
		this.mid = mid;
		this.title = title;
		this.date = date;
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
}

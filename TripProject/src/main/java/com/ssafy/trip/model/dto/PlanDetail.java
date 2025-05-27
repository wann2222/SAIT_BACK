package com.ssafy.trip.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PlanDetail {
	private int pid;
	private int ano;
	private String start;
	private String end;
}

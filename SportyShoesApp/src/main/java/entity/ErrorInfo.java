package com.simplilearn.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo {
	
	private String errorCode;
	private String errorMessage;
	private LocalDate timeStamp;

}

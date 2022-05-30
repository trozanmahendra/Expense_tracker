package com.mgWork.expensetrackerapi.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Component
public class ErrorObject {

	private Integer statusCode;
	private String message;
	private Date timeStamp;
	
}

package com.mgWork.expensetrackerapi.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	@NotBlank(message = "Name should'nt be blank")
	private String name;
	@Email(message  ="Enter a vaild mail adress")
	private String email;
	@NotBlank(message = "password must'nt be blank")
	@Size(min = 4,message = "password must be minimum 4 character long")
	private String password;
	private Long age =0l;
}

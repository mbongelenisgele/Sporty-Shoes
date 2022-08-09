package com.simplilearn.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
	
	@NotNull(message = "User name cannot be null.")
    private String userName;
	@NotNull(message = "User Password cannot be null.")
    private String userPassword;
}

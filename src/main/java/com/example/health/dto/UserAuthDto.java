package com.example.health.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDto {
    @NotBlank(message = "Required for entering")
    @Length(min = 3, max = 15, message = "The length of the email/username must be from 3 to 18 characters")
    private String username;
    @NotBlank(message = "Required for entering")
    @Length(min = 3, max = 15, message = "The length of the username must be from 3 to 18 characters")
    private String password;
}

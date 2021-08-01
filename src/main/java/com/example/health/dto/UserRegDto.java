package com.example.health.dto;


import com.example.health.entity.Role;
import com.example.health.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegDto {
    @NotBlank(message = "Required for entering")
    @Length(min = 3, max = 15, message = "The length of the username must be from 3 to 18 characters")
    private String nameRegDTO;
    @NotBlank(message = "Required for entering")
    @Length(min = 3, max = 15, message = "The length of the password must be from 3 to 18 characters")
    private String passwordRegDTO;
    @NotBlank(message = "Required for entering")
    @Length(min = 3, max = 15, message = "The length of the email must be from 4 to 18 characters")
    @Email(message = "Enter the correct email address")
    private String emailRegDTO;


}

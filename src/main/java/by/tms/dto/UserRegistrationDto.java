package by.tms.dto;

import by.tms.entity.UserRole;
import by.tms.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {
    @NotBlank
    @NotEmpty
    private String nameRegDTO;
    @NotBlank
    @NotEmpty
    private String passwordRegDTO;
    @NotBlank
    @NotEmpty
    private String emailRegDTO;
    private UserRole roleRegDTO;
    private UserStatus statusDTO;
    private boolean deleted;
}

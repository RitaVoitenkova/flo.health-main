package by.tms.service;

import by.tms.dto.UserRegistrationDto;
import by.tms.entity.User;

public interface UserService {
    User getUserById(long id);
    void registrationUser(UserRegistrationDto userRegDTO);
    void setUserDeleted(User user);
    void setUserNotDeleted(String username);
}

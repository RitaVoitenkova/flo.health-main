package by.tms.service;

import by.tms.dto.UserRegistrationDto;
import by.tms.entity.User;
import by.tms.repository.UserRepository;
import by.tms.service.exception.SuchUserIsPresentAlreadyException;
import by.tms.service.exception.UserIsDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registrationUser(UserRegistrationDto userRegDTO) {
        User userFromDB = userRepository.findByUsername(userRegDTO.getNameRegDTO());
        if (userFromDB == null) {
            User user = new User();
            user.setUsername(userRegDTO.getNameRegDTO());
            user.setPassword(passwordEncoder.encode(userRegDTO.getPasswordRegDTO()));
            user.setEmail(userRegDTO.getEmailRegDTO());
            user.setRoles(Collections.singleton(userRegDTO.getRoleRegDTO()));
            user.setDeleted(false);
            userRepository.save(user);
        } else throw new SuchUserIsPresentAlreadyException();

    }


    @Override
    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void setUserDeleted(User user){
        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void setUserNotDeleted(String username){
        userRepository.findByUsername(username).setDeleted(false);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (userRepository.findByUsername(name).isDeleted()){
            throw new UserIsDeletedException();
        }
        return userRepository.findByUsername(name);
    }
}

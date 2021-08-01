package com.example.health.service;



import com.example.health.dto.UserRegDto;
import com.example.health.entity.Role;
import com.example.health.entity.Status;
import com.example.health.entity.User;
import com.example.health.entity.UserSecurityDetails;
import com.example.health.repository.UserRepository;

import com.example.health.service.exception.EmailIsBusyServiceException;
import com.example.health.service.exception.UsernameIsBusyServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Qualifier("userService")
public class UserService implements UserDetailsService {

    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    //метод для авторизации
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist "));
        return UserSecurityDetails.fromUser(user);
    }

//    public User saveUser(User user){
//        String newPassword = passwordEncoder().encode(user.getPassword());
//        user.setPassword(newPassword);
//        return userRepository.save(user);
//    }
    public User registration(UserRegDto userRegDto) throws UsernameIsBusyServiceException, EmailIsBusyServiceException {
        if (userRepository.existsByUsername(userRegDto.getNameRegDTO())) {
            throw new UsernameIsBusyServiceException();
        }
        if (userRepository.existsByEmail(userRegDto.getEmailRegDTO())) {
            throw new EmailIsBusyServiceException();
        }
        User user = modelMapper.map(userRegDto, User.class);
        user.setPassword(passwordEncoder.encode(userRegDto.getPasswordRegDTO()));
        user.setStatus(Status.USER_ACTIVE);
        user.setRole(Role.USER);

//        User userSave = userRepository.save(user);

//        log.info("A new user has been registered: {}", userSave);
        return userRepository.save(user);
    }



//    public User registration(UserRegDto userRegDto) throws UsernameIsBusyServiceException, EmailIsBusyServiceException {
//        if(userRepository.existsByUsername(userRegDto.getNameRegDTO())) {
//            throw new UsernameIsBusyServiceException();
//        }
//        if(userRepository.existsByEmail(userRegDto.getEmailRegDTO())) {
//            throw new EmailIsBusyServiceException();
//        }
//
//        User user = modelMapper.map(userRegDto, User.class);
//        user.setPassword(passwordEncoder.encode(userRegDto.getPasswordRegDTO()));
//        user.setRole(Role.USER);
//        user.setStatus(Status.YOURCYCLE);
//        user.setStatus(Status.WANTPREGNANT);
//        user.setStatus(Status.PREGNANT);
////        user.setRole(Stream.of(new UserRole(1)).collect(Collectors.toSet()));
//
//        return userRepository.save(user);
//    }



    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public User findUserById(long id){
        return userRepository.getById(id);
    }

    public Optional<User> findUserByUserName(String userName){
        return userRepository.findByUsername(userName);
    }


//**

//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public void registrationUser(UserRegDto userRegDTO) {
//        User userFromDB = userRepository.findByUsername(userRegDTO.getNameRegDTO());
//        if (userFromDB == null) {
//            User user = new User();
//            user.setUsername(userRegDTO.getNameRegDTO());
//            user.setPassword(passwordEncoder.encode(userRegDTO.getPasswordRegDTO()));
//            user.setEmail(userRegDTO.getEmailRegDTO());
//            user.setRoles(Collections.singleton(userRegDTO.getRoleRegDTO()));
//            user.setDeleted(false);
//            userRepository.save(user);
//        } else throw new SuchUserIsPresentAlreadyException();
//
//    }
//
//    @Override
//    public User getUserById(long id) {
//        return userRepository.findById(id);
//    }
//
//    public void setUserDeleted(User user){
//        user.setDeleted(true);
//        userRepository.save(user);
//    }
//    public User saveUser(User user){
//        String newPassword = passwordEncoder().encode(user.getPassword());
//        user.setPassword(newPassword);
//        return userRepository.save(user);
//    }
//
//    @Override
//    public void setUserNotDeleted(String username){
//        userRepository.findByUsername(username).setDeleted(false);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        if (userRepository.findByUsername(name).isDeleted()){
//            throw new UserIsDeletedException();
//        }
//        return userRepository.findByUsername(name);
//    }
}

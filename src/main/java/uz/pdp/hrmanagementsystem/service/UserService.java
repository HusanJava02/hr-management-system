package uz.pdp.hrmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementsystem.dto.GenericResponse;
import uz.pdp.hrmanagementsystem.dto.UserModel;
import uz.pdp.hrmanagementsystem.entities.Company;
import uz.pdp.hrmanagementsystem.entities.Roles;
import uz.pdp.hrmanagementsystem.entities.User;
import uz.pdp.hrmanagementsystem.entities.enums.RoleName;
import uz.pdp.hrmanagementsystem.exceptions.DatabaseException;
import uz.pdp.hrmanagementsystem.exceptions.GenericException;
import uz.pdp.hrmanagementsystem.exceptions.ResourceNotFoundException;
import uz.pdp.hrmanagementsystem.exceptions.UserAlreadyExistException;
import uz.pdp.hrmanagementsystem.mappers.RoleMapper;
import uz.pdp.hrmanagementsystem.mappers.UserMapper;
import uz.pdp.hrmanagementsystem.repository.RoleRepository;
import uz.pdp.hrmanagementsystem.repository.UserRepository;
import uz.pdp.hrmanagementsystem.service.publisher.NewUserPublishEvent;
import uz.pdp.hrmanagementsystem.service.publisher.NotificationPublishEvent;
import uz.pdp.hrmanagementsystem.service.publisher.OnRegistrationEventPublisher;
import uz.pdp.hrmanagementsystem.utils.RandomUtils;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final OnRegistrationEventPublisher publisher;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final ApplicationEventPublisher newUserPublisher;

    @Value("86400000")
    private Long expiredDateVerificationCode;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, RoleMapper roleMapper, OnRegistrationEventPublisher publisher, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, ApplicationEventPublisher newUserPublisher) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.newUserPublisher = newUserPublisher;
    }

    public GenericResponse create(UserModel userModel) {
        if (userRepository.existsByEmail(userModel.getEmail())){
            throw new UserAlreadyExistException(String.format("Email already exists, email: %s",userModel.getEmail()));
        }
        User user = userMapper.toEntity(userModel);
        String randomUUID = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmailUUID(randomUUID);
        userRepository.save(validateRoles(RoleName.DIRECTOR,user));
        publisher.publish(new NotificationPublishEvent(userModel.getEmail(),randomUUID,String.format("Email sent, email: %s",user.getEmail())));
        return GenericResponse.builder().success(true).message(String.format("Successfully sent email : %s",userModel.getEmail())).build();
    }

    private User validateRoles(RoleName roleName,User user) {
        Set<Roles> rolesSet = new HashSet<>();
        if (roleRepository.existsByRoleName(roleName)){
            Roles roleUser = roleRepository.findByRoleName(roleName);
            rolesSet.add(roleUser);
            user.setRoles(rolesSet);
        }else {
            Roles director = new Roles();
            director.setRoleName(roleName);
            rolesSet.add(director);
            user.setRoles(rolesSet);
        }
        Company company = user.getCompany();
        user.setCompany(company);
        return user;
    }

    public GenericResponse activateUser(String verificationCode,String email) {
        Date date = new Date(System.currentTimeMillis()+expiredDateVerificationCode);
        if (date.before(new Date())) {
            /*Verificaition code expired*/
            throw new GenericException("Verification code expired, expire Date: " + date);
        }
            /*verification code not expired*/
        if (!userRepository.existsByEmailAndEmailUUID(email,verificationCode))
            throw new DatabaseException("Verfication code is not valid");

        activate(email);
        return GenericResponse.builder().success(true).message("account activated").build();
    }

    private void activate(String email) {
        User user = userRepository.findUserByEmail(email);
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    /*
    Adding User to specific Company
     */
    public UserModel addUserToCompany(UserModel userModel, String directorEmail) {
        String password = RandomUtils.generateRandomPassword();
        User user = userMapper.toEntity(userModel);
        User compDirector = userRepository.findUserByEmail(directorEmail);
        Company company = compDirector.getCompany();
        user.setCompany(company);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsEnabled(true);
        User saved = userRepository.save(user);
        newUserPublisher.publishEvent(new NewUserPublishEvent(userModel.getEmail(),password));
        return userMapper.toModel(saved);
    }

    public UserModel update(UserModel userModel) {
        User userByEmail = userRepository.findUserByEmail(userModel.getEmail());
        if (Objects.isNull(userByEmail))
            throw new ResourceNotFoundException(String.format("User not found , email: %s",userModel.getEmail()));
        return userMapper.toModel(userRepository.save(userMapper.toEntity(userModel)));
    }

    public List<UserModel> getUsers(Integer page) {
        Pageable pageable = PageRequest.of(page,50);
        List<User> content = userRepository.findAll(pageable).getContent();
        return userMapper.toModelList(content);
    }

    public GenericResponse delete(UUID id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return GenericResponse.builder().success(true).message("deleted").build();
        }else throw new ResourceNotFoundException("User not found");
    }
}

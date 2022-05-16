package uz.pdp.hrmanagementsystem.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagementsystem.entities.User;
import uz.pdp.hrmanagementsystem.repository.UserRepository;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByEmail = userRepository.findUserByEmail(username);
        if (Objects.isNull(userByEmail)){
            throw new UsernameNotFoundException(String.format("Username not found our DB email: %s",username));
        }
        return userByEmail;
    }
}

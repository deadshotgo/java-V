package com.example.dom.service;

import com.example.dom.dto.user.UserDto;
import com.example.dom.models.User;
import com.example.dom.exception.UserAlreadyExistException;
import com.example.dom.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> getUsers() {
        List<User> entities = userRepository.findAll();
        return entities.stream()
                .map(User::toModel)
                .collect(Collectors.toList());
    }

    public User getUser(Long id) {
        User entity = userRepository.findById(id).orElse(null);
        if (entity == null) throw new EntityNotFoundException("User with id " + id + " not found");
        return User.toModel(entity);
    }

    public User createUser(UserDto user) {
        User entity = new User();
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(encoder.encode(user.getPassword()));
        entity.setRoles(user.getRole() != null ? "USER" : "ADMIN");
        return User.toModel(userRepository.save(entity));
    }

    public Long deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new InternalError("Something went wrong");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userDetail = userRepository.findByUsername(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + username));
    }

    public User createUserHasAuth(UserDto user) throws UserAlreadyExistException {
        extracted(user);
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setRoles("USER");
        return User.toModel(userRepository.save(userEntity));
    }

    private void extracted(UserDto user) throws UserAlreadyExistException {
        Optional<User> userDB = userRepository.findByUsername(user.getUsername());
        if(userDB.isPresent()) {
            throw new UserAlreadyExistException("User with username " + user.getUsername() + " already exist");
        }
    }
}

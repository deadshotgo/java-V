package com.example.dom.service;

import com.example.dom.dto.user.RequestUser;
import com.example.dom.dto.user.ResponseUser;
import com.example.dom.entity.UserEntity;
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

    public List<ResponseUser> getUsers() {
        List<UserEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(ResponseUser::toModel)
                .collect(Collectors.toList());
    }

    public ResponseUser getUser(Long id) {
        UserEntity entity = userRepository.findById(id).orElse(null);
        if (entity == null) throw new EntityNotFoundException("User with id " + id + " not found");
        return ResponseUser.toModel(entity);
    }

    public ResponseUser createUser(RequestUser user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        return ResponseUser.toModel(userRepository.save(entity));
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
        Optional<UserEntity> userDetail = userRepository.findByUsername(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found " + username));
    }

    public ResponseUser createUserHasAuth(RequestUser user) throws UserAlreadyExistException {
        extracted(user);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setRoles("USER");
        return ResponseUser.toModel(userRepository.save(userEntity));
    }

    private void extracted(RequestUser user) throws UserAlreadyExistException {
        Optional<UserEntity> userDB = userRepository.findByUsername(user.getUsername());
        if(userDB.isPresent()) {
            throw new UserAlreadyExistException("User with username " + user.getUsername() + " already exist");
        }
    }
}

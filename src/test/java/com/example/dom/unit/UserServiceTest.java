package com.example.dom.unit;

import com.example.dom.dto.user.UserDto;
import com.example.dom.models.User;
import com.example.dom.repository.UserRepository;
import com.example.dom.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetCompetitions() {
        // Arrange
        User user = createUSerEntity(1L, "admin 1", "admin@email.com");
        User user2 = createUSerEntity(2L, "admin 2", "admin@email.com");
        when(userRepo.findAll()).thenReturn(Arrays.asList(user, user2));

        // Act
        List<User> result = userService.getUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("admin 1", result.get(0).getUsername());
        assertEquals("admin 2", result.get(1).getUsername());
    }

    @Test
    void testGetUser_WhenIdExists() {
        // Arrange
        Long id = 1L;
        String username = "admin 1";
        User user = createUSerEntity(1L, username, "email.com");
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        // Act
        User response = userService.getUser(id);

        // Assert
        assertEquals(username, response.getUsername());
    }

    @Test
    public void testGetUser_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.getUser(id));
    }

    @Test
    public void testCreateUSer_WhenUsernameIsValid() {
        // Arrange
        Long id = 1L;
        String username = "admin 1";
        String password = "password";
        UserDto requestUser = new UserDto(username, password,"email.com", "ROLE_USER");
        User user = createUSerEntity(id, username, "email.com");
        when(encoder.encode(password)).thenReturn("aklshdjkasjkdasjkdjkashdkjaklmcklnkasjndkl");
        when(userRepo.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.createUser(requestUser);

        // Assert
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testDeleteUser_WhenIdExists() {
        // Arrange
        Long id = 1L;
        // Act
        Long result = userService.deleteUser(id);

        // Assert
        assertEquals(id, result);
        verify(userRepo, times(1)).deleteById(id);
    }

    private User createUSerEntity(Long id, String username, String email) {
        return new User(id, username, "password" , email, "USER_ROLE");
    }
}
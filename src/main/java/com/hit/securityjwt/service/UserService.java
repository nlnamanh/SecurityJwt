package com.hit.securityjwt.service;

import com.hit.securityjwt.dao.User;
import com.hit.securityjwt.dto.UserDTO;
import com.hit.securityjwt.exceptions.BadRequestException;
import com.hit.securityjwt.exceptions.NotFoundException;
import com.hit.securityjwt.repository.UserRepository;
import com.hit.securityjwt.utils.Convert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User createNewUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new BadRequestException("Duplicate username");
        }
        User user1 = new User();
        Convert.fromUserDTOToUser(userDTO, user1);
        return userRepository.save(user1);
    }

    public User deleteUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No user");
        }
        userRepository.delete(user.get());
        return user.get();
    }
}

package com.hit.securityjwt.utils;

import com.hit.securityjwt.dao.User;
import com.hit.securityjwt.dto.UserDTO;

public class Convert {
    public static User fromUserDTOToUser(UserDTO userDTO, User user) {
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        return user;
    }
}

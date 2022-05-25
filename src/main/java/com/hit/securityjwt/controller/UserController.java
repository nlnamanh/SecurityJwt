package com.hit.securityjwt.controller;

import com.hit.securityjwt.dto.UserDTO;
import com.hit.securityjwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/get")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping("/api/users/create")
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createNewUser(userDTO));
    }

    @DeleteMapping("/api/users/delete")
    public ResponseEntity<?> deleteUserById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}

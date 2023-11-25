package com.codehunter.modulithproject.countdown_timer.mapper;

import com.codehunter.modulithproject.countdown_timer.UserDTO;
import com.codehunter.modulithproject.countdown_timer.domain.User;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        return User.builder().id(userDTO.id()).name(userDTO.username()).build();
    }
}

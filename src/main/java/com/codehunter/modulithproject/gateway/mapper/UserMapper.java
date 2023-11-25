package com.codehunter.modulithproject.gateway.mapper;

import com.codehunter.modulithproject.gateway.UserDTO;

public class UserMapper {
    public static com.codehunter.modulithproject.countdown_timer.UserDTO toCoundownTimerUserDTO(UserDTO userDTO) {
        return new com.codehunter.modulithproject.countdown_timer.UserDTO(userDTO.id(), userDTO.username());
    }
}

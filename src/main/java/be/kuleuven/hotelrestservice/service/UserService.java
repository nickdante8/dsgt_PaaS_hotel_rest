package be.kuleuven.hotelrestservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import be.kuleuven.hotelrestservice.data.user.UserDto;
import be.kuleuven.hotelrestservice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getUserById(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .roles(user.getRoles().stream().map(x -> x.getName().name()).collect(Collectors.toList()))
                        .build())
                .orElseThrow();
    }
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .roles(user.getRoles().stream().map(x -> x.getName().name()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());
    }
}


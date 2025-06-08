package be.kuleuven.hotelrestservice.data.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {

    private String id;
    private String username;
    private String email;
    private List<String> roles;
}

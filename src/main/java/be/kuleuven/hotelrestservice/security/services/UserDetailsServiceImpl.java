package be.kuleuven.hotelrestservice.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import be.kuleuven.hotelrestservice.entity.User;
import be.kuleuven.hotelrestservice.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Transactional
  public UserDetails loadUserByUserId(String id) throws UsernameNotFoundException {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + id));

    return UserDetailsImpl.build(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }
}

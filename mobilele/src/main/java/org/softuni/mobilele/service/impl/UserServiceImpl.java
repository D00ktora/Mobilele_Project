package org.softuni.mobilele.service.impl;

import org.modelmapper.ModelMapper;
import org.softuni.mobilele.model.dto.LoggedUserDTO;
import org.softuni.mobilele.model.dto.UserLoginDTO;
import org.softuni.mobilele.model.dto.UserRegistrationDTO;
import org.softuni.mobilele.model.entity.UserEntity;
import org.softuni.mobilele.model.entity.UserRole;
import org.softuni.mobilele.repository.UserRepository;
import org.softuni.mobilele.service.RoleService;
import org.softuni.mobilele.service.UserService;
import org.softuni.mobilele.user.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper modelMapper;
  private CurrentUser currentUser;
@Autowired
  public UserServiceImpl(
        UserRepository userRepository,
        RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, CurrentUser currentUser) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.passwordEncoder = passwordEncoder;
    this.modelMapper = modelMapper;
  this.currentUser = currentUser;
}

  @Override
  public void registerUser(UserRegistrationDTO userRegistrationDTO) {
    UserEntity newUser = map(userRegistrationDTO);
    UserRole userRole = new UserRole();

    if (userRepository.count() > 0) {
      userRole = this.roleService.getUserRole(2L);
    } else {
      userRole = this.roleService.getUserRole(1L);
    }

    newUser.setRole(userRole)
            .setCreated(LocalDateTime.now())
            .setModified(LocalDateTime.now());

    UserEntity savedUser = userRepository.save(newUser);
    currentUser.setLoggedIn(savedUser.isActive()).setName(savedUser.getFirstName());
  }

  @Override
  public boolean login(UserLoginDTO loginDTO) {
    Optional<UserEntity> optionalUserEntity = userRepository.getByEmail(loginDTO.getEmail());

    if (optionalUserEntity.isEmpty()) {
      return false;
    }

    boolean passwordMatcher = passwordEncoder.matches(loginDTO.getPassword(), optionalUserEntity.get().getPassword());

    if (passwordMatcher) {
      currentUser.login(optionalUserEntity.get());
//      currentUser.setLoggedIn(true).
//              setName(optionalUserEntity.get().getFirstName())
//              .setRole(optionalUserEntity.get().getRole())
//              .setEmail(optionalUserEntity.get().getEmail());
    } else {
      logout();
    }

    return passwordMatcher;
  }

  public void logout() {
    currentUser.clear();
  }

  @Override
  public UserEntity getByEmail(String email) {
    return userRepository.getByEmail(email).orElse(null);
  }

  private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
    return new UserEntity()
        .setActive(true)
        .setFirstName(userRegistrationDTO.getFirstName())
        .setLastName(userRegistrationDTO.getLastName())
        .setEmail(userRegistrationDTO.getEmail())
        .setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
  }
}

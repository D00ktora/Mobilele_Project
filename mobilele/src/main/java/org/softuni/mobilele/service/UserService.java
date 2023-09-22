package org.softuni.mobilele.service;

import org.softuni.mobilele.model.dto.UserLoginDTO;
import org.softuni.mobilele.model.dto.UserRegistrationDTO;
import org.softuni.mobilele.model.entity.UserEntity;

public interface UserService {

  void registerUser(UserRegistrationDTO userRegistrationDTO);
  UserEntity login(UserLoginDTO loginDTO);

}
package com.user.registration.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.registration.api.dto.UserDTO;
import com.user.registration.api.entity.UserEntity;
import com.user.registration.api.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDTO createUser(UserDTO userDto) {
		UserEntity userEntity = UserEntity.builder()
							.firstName(userDto.getFirstName())
							.lastName(userDto.getLastName())
							.email(userDto.getEmail())
							.mobileNumber(userDto.getMobileNumber())
							.build();
		this.userRepository.save(userEntity);
		return UserDTO.builder()
				.firstName(userEntity.getFirstName())
				.lastName(userEntity.getLastName())
				.email(userEntity.getEmail())
				.mobileNumber(userEntity.getMobileNumber())
				.build();
	}

}

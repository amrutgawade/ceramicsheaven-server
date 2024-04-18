package com.ceramicsheaven.services;


import com.ceramicsheaven.config.JwtProvider;
import com.ceramicsheaven.exceptions.UserException;
import com.ceramicsheaven.model.Address;
import com.ceramicsheaven.model.User;
import com.ceramicsheaven.repositories.AddressRepository;
import com.ceramicsheaven.repositories.UserRepository;
import com.ceramicsheaven.requests.UpdatePasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImplementation implements UserService{
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private AddressRepository addressRepository;
	@Autowired
	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider, AddressRepository addressRepository) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
		this.addressRepository = addressRepository;
	}

	@Override
	public User findById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserException("User Not Found With Id -"+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UserException("User Not Found With This Exception"+email);
		}
		System.out.println("Found User : "+user.toString());
		return user;
	}

	@Override
	public User updateUser(String jwt,User user) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User existingUser = userRepository.findByEmail(email);

		if(existingUser == null){
			throw  new UserException("User Not Found With "+user.getEmail()+ "Email" );
		}else{
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setGender(user.getGender());
			existingUser.setMobile(user.getMobile());
			existingUser.setRole(user.getRole());
		}
		return userRepository.save(existingUser);
	}

	@Override
	public String updatePassword(String jwt,UpdatePasswordRequest updatePasswordRequest) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User existingUser = userRepository.findByEmail(email);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (encoder.matches(updatePasswordRequest.getCurrentPassword(), existingUser.getPassword())) {
			String newPasswordHash = encoder.encode(updatePasswordRequest.getNewPassword());
			existingUser.setPassword(newPasswordHash);
			userRepository.save(existingUser);
			return "Password updated successfully";
		} else {
			// Passwords don't match
			return "Current password is not valid";
		}

	}

	@Override
	public String addAddress(String jwt, Address address) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User existingUser = userRepository.findByEmail(email);

		Address newAddress = new Address();
		newAddress.setUser(existingUser);
		newAddress.setFirstName(address.getFirstName());
		newAddress.setLastName(address.getLastName());
		newAddress.setStreetAddress(address.getStreetAddress());
		newAddress.setCity(address.getCity());
		newAddress.setState(address.getState());
		newAddress.setZipCode(address.getZipCode());
		newAddress.setMobile(address.getMobile());
		addressRepository.save(newAddress);

		return "Address Added Successfully";
	}

	@Override
	public List<Address> getAddress(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		User existingUser = userRepository.findByEmail(email);
		return addressRepository.findAllByUserId(existingUser.getId());
	}

	@Override
	public String removeAddress(String jwt, Long addressId) throws UserException {
		addressRepository.deleteById(addressId);
		return "Address Deleted Successfully";
	}

}

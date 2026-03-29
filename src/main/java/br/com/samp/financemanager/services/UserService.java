package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.request.UserRequest;
import br.com.samp.financemanager.dto.response.UserResponse;
import br.com.samp.financemanager.dto.mapstruct.UserMapper;
import br.com.samp.financemanager.model.Address;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.UserRepository;
import br.com.samp.financemanager.exceptions.DataBaseException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.samp.financemanager.model.enums.UserRole.USER;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> findAll() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse findById(long id) {
        Optional<User> user = userRepository.findById(id);

        return userMapper.toUserResponse(user.orElseThrow(()-> new ResourceNotFoundException(id)));
    }

    public UserResponse save(UserRequest userRequest) {
        User user = saveEntity(userRequest);
        return userMapper.toUserResponse(user);
    }

    public User saveEntity(UserRequest userRequest) {
        Address address = addressService.saveAddress(userRequest.zipcode(),userRequest.addressNumber());

        User userEntity = userMapper.toEntity(userRequest);

        var passwordEncode = passwordEncoder.encode(userRequest.password());

        userEntity.setPassword(passwordEncode);
        userEntity.setRole(USER);
        userEntity.setAddress(address);

        return userRepository.save(userEntity);
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));

        var address = userEntity.getAddress();

        var userZipCode = address.getZipCode();
        var userNumber = address.getNumber();

        if (!Objects.equals(userZipCode, userRequest.zipcode())||
                !Objects.equals(userNumber,userRequest.addressNumber())) {

            Address newAddress = addressService.saveAddress(
                    userRequest.zipcode(),
                    userRequest.addressNumber()
            );

            userEntity.setAddress(newAddress);
        }

        userEntity.setName(userRequest.name());
        userEntity.setEmail(userRequest.email());
        userEntity.setCpf(userRequest.cpf());
        userEntity.setPassword(passwordEncoder.encode(userRequest.password()));

        return userMapper.toUserResponse(userRepository.save(userEntity));
    }

    public void delete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));

        try {
            userRepository.delete(user);
        }catch(DataIntegrityViolationException e){

            throw new DataBaseException(e.getMessage());

        }
    }
}

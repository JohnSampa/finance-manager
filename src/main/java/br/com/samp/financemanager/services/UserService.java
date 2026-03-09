package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.UserRequest;
import br.com.samp.financemanager.dto.UserResponse;
import br.com.samp.financemanager.dto.mapstruct.UserMapper;
import br.com.samp.financemanager.model.Address;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.UserRepository;
import br.com.samp.financemanager.exceptions.DataBaseException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressService addressService;

    public List<UserResponse> findAll() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse findById(long id) {
        Optional<User> user = userRepository.findById(id);

        return userMapper.toUserResponse(user.orElseThrow(()-> new ResourceNotFoundException(id)));
    }

    public UserResponse save(UserRequest userRequest) {
        Address address = addressService.saveAddress(userRequest.zipcode(),userRequest.addressNumber());

        User userEntity = userMapper.toEntity(userRequest);

        userEntity.setAddress(address);

        return userMapper.toUserResponse(userRepository.save(userEntity));
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));

        var userZipCode = userEntity.getAddress().getZipCode();
        var userNumber = userEntity.getAddress().getNumber();

        if (!Objects.equals(userZipCode, userRequest.zipcode())&&
                Objects.equals(userNumber,userRequest.addressNumber())) {

            Address address = addressService.saveAddress(
                    userRequest.zipcode(),
                    userRequest.addressNumber()
            );

            userEntity.setAddress(address);
        }

        userEntity.setName(userRequest.name());
        userEntity.setEmail(userRequest.email());
        userEntity.setCpf(userRequest.cpf());
        userEntity.setPassword(userRequest.password());

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

package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.UserRequest;
import br.com.samp.financemanager.dto.UserResponse;
import br.com.samp.financemanager.dto.mapstruct.UserMapper;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public UserResponse findById(long id) {
        Optional<User> user = userRepository.findById(id);

        return userMapper.toUserResponse(user.orElse(null));
    }

    public UserResponse save(UserRequest userRequest) {
        User userEntity = userMapper.toEntity(userRequest);
        return userMapper.toUserResponse(userRepository.save(userEntity));
    }

}

package com.hv.example.microservice.domain.user;

import com.hv.example.microservice.dao.repository.UserRepository;
import com.hv.example.microservice.domain.user.dto.UserDto;
import com.hv.example.microservice.infrastructure.logging.WSLog;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserHelper {

    private final WSLog wsLog;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    public UserHelper(WSLog wsLog, ModelMapper mapper, UserRepository userRepository) {
        this.wsLog = wsLog;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(u->mapper.map(u,UserDto.class)).collect(Collectors.toUnmodifiableList());
    }
}

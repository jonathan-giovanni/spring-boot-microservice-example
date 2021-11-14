package com.hv.example.microservice.domain.user;

import com.hv.example.microservice.dao.repository.UserRepository;
import com.hv.example.microservice.dao.spec.UserSpecification;
import com.hv.example.microservice.domain.user.dto.UserDto;
import com.hv.example.microservice.domain.user.dto.UserFilterDto;
import com.hv.example.microservice.infrastructure.logging.WSLog;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserHelper {

    private final WSLog wsLog;
    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final UserSpecification userSpecification;

    public UserHelper(WSLog wsLog, ModelMapper mapper, UserRepository userRepository, UserSpecification userSpecification) {
        this.wsLog = wsLog;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userSpecification = userSpecification;
    }


    public Page<UserDto> findAll(Pageable paging, Optional<UserFilterDto> filter) {
        if(filter.isPresent()) {
            return userRepository.findAll(userSpecification.filter(filter.get()),paging).map(u->mapper.map(u,UserDto.class));
        }
        return userRepository.findAll(paging).map(u->mapper.map(u,UserDto.class));
    }
}

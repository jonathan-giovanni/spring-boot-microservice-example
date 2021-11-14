package com.hv.example.microservice.repository;

import com.hv.example.microservice.dao.entity.UserEntity;
import com.hv.example.microservice.dao.repository.UserRepository;
import com.hv.example.microservice.dao.spec.UserSpecification;
import com.hv.example.microservice.domain.user.dto.UserFilterDto;
import com.hv.example.microservice.util.AppUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;


@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UserRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSpecification userSpecification;


    @Test
    void Test_01_FindAllUsers() {
        log.info(AppUtil.getMethodWithClass());

        log.info("Actors: {}",userRepository.count());
        userRepository.findAll().forEach(userEntity -> log.info(userEntity.toString()));

        Assertions.assertTrue(true);
    }



    @Test
    void Test_02_SaveUserAndFindById(){
        log.info(AppUtil.getMethodWithClass());
        //log before save
        log.info("Users before save: {}",userRepository.count());

        var userEntity = new UserEntity(AppUtil.generateId());
        userEntity.setName("jonathan");
        userEntity.setEmail(userEntity.getName()+"@example.com");
        userEntity.setEnabled(true);
        userEntity.setCreatedAt(new Date());
        //encoding using bcrypt
        userEntity.setPassword(new BCryptPasswordEncoder().encode("password"));

        //save
        userEntity =  userRepository.saveAndFlush(userEntity);
        //log count records after save
        log.info("Users after  save: {}",userRepository.count());

        var userResult = userRepository.findById(userEntity.getId());
        if(userResult.isPresent()){
            log.info("FOUND: {}",userResult.get());
        }else{
            Assertions.fail();
        }
    }


    @Test
    void Test_03_SearchByCriteria(){
        log.info(AppUtil.getMethodWithClass());
        var filterDto = new UserFilterDto();
        filterDto.setFilter("jon");

        userRepository.findAll(userSpecification.filter(filterDto)).forEach(user -> log.info(user.toString()));
    }


}

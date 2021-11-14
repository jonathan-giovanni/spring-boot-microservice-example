package com.hv.example.microservice.config;

import com.hv.example.microservice.dao.entity.UserEntity;
import com.hv.example.microservice.dao.repository.UserRepository;
import com.hv.example.microservice.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;

@Configuration
@Slf4j
public class InitDataConfig {

    private static boolean initialized = false;

    private final UserRepository userRepository;

    public InitDataConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Init data just one time
     */
    @PostConstruct
    public void initData(){
        if(initialized) return;
        log.info(AppUtil.getMethodWithClass());
        for(int i=0;i<20;i++){
            var userEntity = new UserEntity(AppUtil.generateId());
            userEntity.setName((new String[]{"jonathan", "jony","giovanni","jessica","jeniffer","tania","bob","eduard","richard"})[new Random().nextInt(8)]+i);
            userEntity.setEmail(userEntity.getName()+(new Random().nextInt(200))+"@example.com");
            userEntity.setEnabled(true);
            userEntity.setCreatedAt(new Date());
            //encoding using bcrypt
            userEntity.setPassword(new BCryptPasswordEncoder().encode("pass"+i));
            //saving
            userRepository.save(userEntity);
        }
        initialized = true;
    }

}

package com.hv.example.microservice.dao.repository;

import com.hv.example.microservice.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> , JpaSpecificationExecutor<UserEntity> {

}

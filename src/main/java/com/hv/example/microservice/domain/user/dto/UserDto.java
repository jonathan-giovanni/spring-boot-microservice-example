package com.hv.example.microservice.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter @NoArgsConstructor
@ToString
public class UserDto {

    private String username;
    private String email;
    private boolean enabled;
    private Date createdAt;

}

package com.hv.example.microservice.domain.user;

import com.hv.example.microservice.constants.AppConstants;
import com.hv.example.microservice.domain.user.dto.UserDto;
import com.hv.example.microservice.infrastructure.logging.LogCategory;
import com.hv.example.microservice.infrastructure.logging.WSLog;
import com.hv.example.microservice.util.AppUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final WSLog wsLog;
    private final UserHelper userHelper;

    public UserService(WSLog wsLog, UserHelper userHelper) {
        this.wsLog = wsLog;
        this.userHelper = userHelper;
    }

    public ResponseEntity<List<UserDto>> findAll() {
        wsLog.info(LogCategory.SERVICE,String.format(AppConstants.MSG_PROCESS_STARTED, AppUtil.getMethodName(2)));
        return ResponseEntity.ok(userHelper.findAll());
    }
}

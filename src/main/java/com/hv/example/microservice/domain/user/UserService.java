package com.hv.example.microservice.domain.user;

import com.hv.example.microservice.constants.AppConstants;
import com.hv.example.microservice.domain.user.dto.UserDto;
import com.hv.example.microservice.domain.user.dto.UserFilterDto;
import com.hv.example.microservice.infrastructure.logging.LogCategory;
import com.hv.example.microservice.infrastructure.logging.WSLog;
import com.hv.example.microservice.util.AppUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final WSLog wsLog;
    private final UserHelper userHelper;

    public UserService(WSLog wsLog, UserHelper userHelper) {
        this.wsLog = wsLog;
        this.userHelper = userHelper;
    }

    public ResponseEntity<Page<UserDto>> findAll(Integer pageNo, Integer pageSize, String sortBy, Optional<UserFilterDto> filter) {
        wsLog.info(LogCategory.SERVICE,String.format(AppConstants.MSG_PROCESS_STARTED, AppUtil.getMethodName(2)));

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

        return ResponseEntity.ok(userHelper.findAll(paging, filter));
    }
}

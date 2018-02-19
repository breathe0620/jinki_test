package kr.mubeat.cms.service.manager.user.impl;

import kr.mubeat.cms.domain.manager.user.User;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.manager.user.UserDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.manager.user.UserRepository;
import kr.mubeat.cms.service.manager.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getUserList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(userRepository.getUserList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserDetail(Long userNo) {
        return userRepository.findOne(userNo);
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateUserState(Long userNo, String stateCode) {
        if (userRepository.updateUserState(userNo, stateCode) <= 0) {
            return new Result(EnumResCode.E003);
        } else {
            return new Result(EnumResCode.OK);
        }
    }
}

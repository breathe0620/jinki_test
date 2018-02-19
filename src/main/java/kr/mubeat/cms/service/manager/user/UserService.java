package kr.mubeat.cms.service.manager.user;

import kr.mubeat.cms.domain.manager.user.User;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
public interface UserService {

    CustomPageResult getUserList(CustomSearchParam searchParam);

    User getUserDetail(Long userNo);

    Result updateUserState(Long userNo, String stateCode);

}

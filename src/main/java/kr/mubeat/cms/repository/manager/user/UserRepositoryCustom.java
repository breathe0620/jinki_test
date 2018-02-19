package kr.mubeat.cms.repository.manager.user;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.manager.user.UserDTO;
import org.springframework.data.domain.Page;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
public interface UserRepositoryCustom {

    Page<UserDTO> getUserList(CustomSearchParam searchParam);

    Long updateUserState(Long userNo, String stateCode);

}

package kr.mubeat.cms.service.common;

import kr.mubeat.cms.domain.manager.admin.Admin;
import kr.mubeat.cms.dto.manager.admin.AdminDTO;
import kr.mubeat.cms.dto.Result;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 * CommonService
 */
public interface CommonService extends UserDetailsService {
    Result signupSuperAdmin(AdminDTO input);
    Result signupAdmin(Admin input);
    boolean isExistSuperUser();
}

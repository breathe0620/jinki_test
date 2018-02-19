package kr.mubeat.cms.service.manager.admin;

import kr.mubeat.cms.domain.manager.admin.Admin;
import kr.mubeat.cms.dto.manager.admin.AdminDTO;
import kr.mubeat.cms.dto.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by doohwan.yoo on 2017. 5. 26..
 */
public interface AdminService {
    Page<AdminDTO> getAdminList(Pageable pageable);
    Admin getAdminDetail(Long adminNo);
    Result updateAdminInfo(Long adminNo, Admin input);
//    Result changeAdminPwd(Admin input); // TODO Session 에서 값을 읽어와 비교 처리 해야 함
}

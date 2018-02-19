package kr.mubeat.cms.repository.manager.admin;

import kr.mubeat.cms.dto.manager.admin.AdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 */
public interface AdminRepositoryCustom {

    long getSAdminCount();
    Page<AdminDTO> getAdminList(Pageable pageable);
}

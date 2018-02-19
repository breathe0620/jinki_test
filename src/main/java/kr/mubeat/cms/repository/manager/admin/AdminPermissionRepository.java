package kr.mubeat.cms.repository.manager.admin;

import kr.mubeat.cms.domain.manager.admin.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by doohwan.yoo on 2017. 5. 26..
 */
@Repository
public interface AdminPermissionRepository extends JpaRepository<AdminPermission, Long> {
}

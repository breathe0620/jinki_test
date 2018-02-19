package kr.mubeat.cms.repository.manager.admin;

import kr.mubeat.cms.domain.manager.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 * AdminRepository
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {

     Admin findByAdminId(String adminId);
     Admin findByAdminNo(Long adminNo);

}

package kr.mubeat.cms.repository.manager.admin;

import kr.mubeat.cms.domain.manager.admin.AdminSecret;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by moonkyu.lee on 2017. 7. 13..
 */
public interface AdminSecretRepository extends JpaRepository<AdminSecret, String> {

}

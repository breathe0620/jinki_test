package kr.mubeat.cms.repository.manager.user;

import kr.mubeat.cms.domain.manager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {



}

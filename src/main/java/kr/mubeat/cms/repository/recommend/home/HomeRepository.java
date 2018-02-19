package kr.mubeat.cms.repository.recommend.home;

import kr.mubeat.cms.domain.recommend.home.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
@Repository
public interface HomeRepository extends JpaRepository<Home, Integer>, HomeRepositoryCustom {
}

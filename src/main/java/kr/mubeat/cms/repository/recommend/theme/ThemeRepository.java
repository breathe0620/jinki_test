package kr.mubeat.cms.repository.recommend.theme;

import kr.mubeat.cms.domain.recommend.theme.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer>, ThemeRepositoryCustom {
}

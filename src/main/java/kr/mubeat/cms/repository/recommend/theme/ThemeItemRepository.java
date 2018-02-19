package kr.mubeat.cms.repository.recommend.theme;

import kr.mubeat.cms.domain.recommend.theme.ThemeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 11. 6..
 */
@Repository
public interface ThemeItemRepository extends JpaRepository<ThemeItem, Integer>, ThemeItemRepositoryCustom {
}

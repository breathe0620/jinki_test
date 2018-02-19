package kr.mubeat.cms.repository.recommend.weekprogram;

import kr.mubeat.cms.domain.recommend.weekprogram.WeekProgramItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 11. 9..
 */
@Repository
public interface WeekProgramItemRepository extends JpaRepository<WeekProgramItem, Integer>, WeekProgramItemRepositoryCustom {
}

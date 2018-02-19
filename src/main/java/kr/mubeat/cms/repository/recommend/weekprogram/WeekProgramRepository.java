package kr.mubeat.cms.repository.recommend.weekprogram;

import kr.mubeat.cms.domain.recommend.weekprogram.WeekProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
@Repository
public interface WeekProgramRepository extends JpaRepository<WeekProgram, Integer>, WeekProgramRepositoryCustom {
}

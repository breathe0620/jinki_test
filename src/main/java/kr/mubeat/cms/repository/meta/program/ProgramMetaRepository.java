package kr.mubeat.cms.repository.meta.program;

import kr.mubeat.cms.domain.meta.program.ProgramMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 21..
 */
@Repository
public interface ProgramMetaRepository extends JpaRepository<ProgramMeta, String>, ProgramMetaRepositoryCustom {
}

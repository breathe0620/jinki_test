package kr.mubeat.cms.repository.meta.clip;

import kr.mubeat.cms.domain.meta.clip.Clip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by doohwan.yoo on 2017. 4. 26..
 * 메타 관리 레포지터리
 */
@Repository
public interface ClipRepository extends JpaRepository<Clip, String>, ClipRepositoryCustom {
}

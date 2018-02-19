package kr.mubeat.cms.repository.meta.clip;

import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 21..
 */
@Repository
public interface ClipMetaRepository extends JpaRepository<ClipMeta, Long> {
}

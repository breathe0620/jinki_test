package kr.mubeat.cms.repository.meta.clip;

import kr.mubeat.cms.domain.meta.clip.ClipMetaLang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 8..
 */
@Repository
public interface ClipMetaLangRepository extends JpaRepository<ClipMetaLang, Long> {
}

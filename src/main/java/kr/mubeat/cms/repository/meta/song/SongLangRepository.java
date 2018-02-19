package kr.mubeat.cms.repository.meta.song;

import kr.mubeat.cms.domain.meta.song.SongLang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 7..
 */
@Repository
public interface SongLangRepository extends JpaRepository<SongLang, Long> {
}

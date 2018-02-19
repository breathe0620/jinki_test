package kr.mubeat.cms.repository.meta.album;

import kr.mubeat.cms.domain.meta.album.AlbumLang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 7..
 */
@Repository
public interface AlbumLangRepository extends JpaRepository<AlbumLang, Long> {
}

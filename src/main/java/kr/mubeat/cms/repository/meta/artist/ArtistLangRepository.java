package kr.mubeat.cms.repository.meta.artist;

import kr.mubeat.cms.domain.meta.artist.ArtistLang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 7..
 */
@Repository
public interface ArtistLangRepository extends JpaRepository<ArtistLang, Long> {
}

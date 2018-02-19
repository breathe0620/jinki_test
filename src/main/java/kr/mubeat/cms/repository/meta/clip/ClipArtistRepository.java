package kr.mubeat.cms.repository.meta.clip;

import kr.mubeat.cms.domain.meta.clip.ClipArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 6. 30..
 */
@Repository
public interface ClipArtistRepository extends JpaRepository<ClipArtist, Long>, ClipArtistRepositoryCustom {
}

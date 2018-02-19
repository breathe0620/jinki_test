package kr.mubeat.cms.repository.meta.song;

import kr.mubeat.cms.domain.meta.song.SongArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 7. 21..
 */
@Repository
public interface SongArtistRepository extends JpaRepository<SongArtist, Long>, SongArtistRepositoryCustom {
}

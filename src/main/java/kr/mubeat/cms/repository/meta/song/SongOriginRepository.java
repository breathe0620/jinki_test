package kr.mubeat.cms.repository.meta.song;

import kr.mubeat.cms.domain.meta.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongOriginRepository  extends JpaRepository<Song, Long> {
    Song findByOriginId(Long originId);
}

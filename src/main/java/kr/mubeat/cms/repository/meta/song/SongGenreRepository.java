package kr.mubeat.cms.repository.meta.song;

import kr.mubeat.cms.domain.meta.song.SongGenre;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by doohwan.yoo on 2017. 5. 16..
 */
public interface SongGenreRepository extends JpaRepository<SongGenre, String> {
}

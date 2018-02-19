package kr.mubeat.cms.repository.meta.song;

import kr.mubeat.cms.domain.meta.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {

//    Page<Song> findByOrderByCreateDtDesc(Pageable pageable);
    Song findBySongId(Long songId);
    List<Song> findAllByOriginIdAndDeleteYn(Long originId, String yn);
}

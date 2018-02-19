package kr.mubeat.cms.repository.meta.artist;

import kr.mubeat.cms.domain.meta.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>, ArtistRepositoryCustom {

    Artist findByArtistId(Long artistId);
    List<Artist> findAllByOriginIdAndDeleteYn(Long originId, String yn);
}

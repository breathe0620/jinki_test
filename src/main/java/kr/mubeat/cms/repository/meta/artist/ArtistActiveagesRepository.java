package kr.mubeat.cms.repository.meta.artist;

import kr.mubeat.cms.domain.meta.artist.ArtistActiveages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 6. 8..
 */
public interface ArtistActiveagesRepository extends JpaRepository<ArtistActiveages, Long> {
    List<ArtistActiveages> findAllByArtistIdOrderByActiveAgesAsc(Long artistId);
}

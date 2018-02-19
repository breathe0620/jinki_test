package kr.mubeat.cms.repository.meta.artist;

import kr.mubeat.cms.domain.meta.artist.SimilarArtist;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by doohwan.yoo on 2017. 6. 5..
 */
public interface SimilarArtistRepository extends CrudRepository<SimilarArtist, Long> {
    void deleteByArtistIdAndSimilarArtistId(Long artistId, Long similarArtistId);
}

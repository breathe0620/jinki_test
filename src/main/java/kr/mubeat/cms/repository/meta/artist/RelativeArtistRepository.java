package kr.mubeat.cms.repository.meta.artist;

import kr.mubeat.cms.domain.meta.artist.RelativeArtist;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by doohwan.yoo on 2017. 6. 5..
 */
public interface RelativeArtistRepository extends CrudRepository<RelativeArtist, Long> {
    void deleteByArtistIdAndRelativeArtistId(Long artistId, Long relativeArtistId);
}

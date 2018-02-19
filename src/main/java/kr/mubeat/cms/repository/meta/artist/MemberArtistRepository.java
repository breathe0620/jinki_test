package kr.mubeat.cms.repository.meta.artist;

import kr.mubeat.cms.domain.meta.artist.MemberArtist;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by doohwan.yoo on 2017. 6. 5..
 */
public interface MemberArtistRepository extends CrudRepository<MemberArtist, Long> {
    void deleteByArtistIdAndMemberArtistId(Long artistId, Long memberArtistId);
}

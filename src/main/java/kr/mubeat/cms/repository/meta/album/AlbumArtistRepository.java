package kr.mubeat.cms.repository.meta.album;

import kr.mubeat.cms.domain.meta.album.AlbumArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 7. 19..
 */
@Repository
public interface AlbumArtistRepository extends JpaRepository<AlbumArtist, Long>, AlbumArtistRepositoryCustom {
}

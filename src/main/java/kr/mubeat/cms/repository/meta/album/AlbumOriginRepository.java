package kr.mubeat.cms.repository.meta.album;

import kr.mubeat.cms.domain.meta.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumOriginRepository extends JpaRepository<Album, Long> {
    Album findByOriginId(Long originId);
}

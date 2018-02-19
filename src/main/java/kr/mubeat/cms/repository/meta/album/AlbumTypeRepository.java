package kr.mubeat.cms.repository.meta.album;

import kr.mubeat.cms.domain.meta.album.AlbumType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by minisv on 2017. 6. 8..
 */
public interface AlbumTypeRepository extends JpaRepository<AlbumType, Integer> {
}

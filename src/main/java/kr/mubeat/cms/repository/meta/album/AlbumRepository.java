package kr.mubeat.cms.repository.meta.album;

import kr.mubeat.cms.domain.meta.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 8..
 */
@Repository // TODO 리파지토리에서 마스터 슬레이브 어떻게 지정해서 가져올지 연구 필요
public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumRepositoryCustom {

    Album findByAlbumId(Long albumId);
    List<Album> findAllByOriginIdAndDeleteYn(Long originId, String yn);

}

package kr.mubeat.cms.repository.meta.artist;


import kr.mubeat.cms.domain.meta.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistOriginRepository extends JpaRepository<Artist, Long> {
    Artist findByOriginId(Long originId);
}

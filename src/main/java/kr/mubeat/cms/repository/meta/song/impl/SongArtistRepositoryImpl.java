package kr.mubeat.cms.repository.meta.song.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.domain.meta.song.QSongArtist;
import kr.mubeat.cms.domain.meta.song.SongArtist;
import kr.mubeat.cms.repository.meta.song.SongArtistRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 7. 21..
 */
public class SongArtistRepositoryImpl extends QueryDslRepositorySupport implements SongArtistRepositoryCustom {

    public SongArtistRepositoryImpl() {
        super(SongArtist.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Long deleteBySongId(Long songId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSongArtist songArtist = QSongArtist.songArtist;

        return queryFactory.delete(songArtist).where(songArtist.songId.eq(songId)).execute();
    }
}

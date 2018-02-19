package kr.mubeat.cms.repository.meta.album.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.domain.meta.album.AlbumArtist;
import kr.mubeat.cms.domain.meta.album.QAlbumArtist;
import kr.mubeat.cms.repository.meta.album.AlbumArtistRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 7. 19..
 */
public class AlbumArtistRepositoryImpl extends QueryDslRepositorySupport implements AlbumArtistRepositoryCustom {

    public AlbumArtistRepositoryImpl() {
        super(AlbumArtist.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Long deleteByAlbumId(Long albumId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbumArtist albumArtist = QAlbumArtist.albumArtist;

        return queryFactory.delete(albumArtist).where(albumArtist.albumId.eq(albumId)).execute();
    }
}

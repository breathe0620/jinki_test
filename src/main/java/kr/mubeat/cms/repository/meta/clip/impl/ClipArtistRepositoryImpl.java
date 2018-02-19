package kr.mubeat.cms.repository.meta.clip.impl;

import com.querydsl.jpa.impl.JPADeleteClause;
import kr.mubeat.cms.domain.meta.clip.ClipArtist;
import kr.mubeat.cms.domain.meta.clip.QClipArtist;
import kr.mubeat.cms.repository.meta.clip.ClipArtistRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 7. 6..
 */
@Repository
public class ClipArtistRepositoryImpl extends QueryDslRepositorySupport implements ClipArtistRepositoryCustom {

    public ClipArtistRepositoryImpl() {
        super(ClipArtist.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Long deleteByclipMetaId(Long clipMetaId) {

        QClipArtist clipArtist = QClipArtist.clipArtist;

        return new JPADeleteClause(this.getEntityManager(), clipArtist)
                .where(clipArtist.clipMetaId.eq(clipMetaId))
                .execute();
    }
}

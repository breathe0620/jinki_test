package kr.mubeat.cms.repository.meta.clip.impl;

import com.querydsl.jpa.impl.JPADeleteClause;
import kr.mubeat.cms.domain.meta.clip.ClipSong;
import kr.mubeat.cms.domain.meta.clip.QClipSong;
import kr.mubeat.cms.repository.meta.clip.ClipSongRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 7. 6..
 */
@Repository
public class ClipSongRepositoryImpl extends QueryDslRepositorySupport implements ClipSongRepositoryCustom {

    public ClipSongRepositoryImpl() {
        super(ClipSong.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Long deleteByclipMetaId(Long clipMetaId) {

        QClipSong clipSong = QClipSong.clipSong;

        return new JPADeleteClause(this.getEntityManager(), clipSong)
                .where(clipSong.clipMetaId.eq(clipMetaId))
                .execute();
    }
}

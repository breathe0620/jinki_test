package kr.mubeat.cms.repository.recommend.weekprogram.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.domain.meta.clip.QClipMeta;
import kr.mubeat.cms.domain.meta.clip.QClipMetaLang;
import kr.mubeat.cms.domain.recommend.weekprogram.QWeekProgramItem;
import kr.mubeat.cms.domain.recommend.weekprogram.WeekProgramItem;
import kr.mubeat.cms.dto.recommend.program.ClipDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.recommend.weekprogram.WeekProgramItemRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 9..
 */
@Repository
public class WeekProgramItemRepositoryImpl extends QueryDslRepositorySupport implements WeekProgramItemRepositoryCustom {

    public WeekProgramItemRepositoryImpl() {
        super(WeekProgramItem.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Long deleteByRecommendProgramId(Integer recommendProgramId) {

        QWeekProgramItem weekProgramItem = QWeekProgramItem.weekProgramItem;

        return new JPADeleteClause(this.getEntityManager(), weekProgramItem)
                .where(weekProgramItem.recommendProgramId.eq(recommendProgramId))
                .execute();
    }

    @Override
    public List<String> getProgramBroadDates(String programId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;

        return queryFactory.select(
            Projections.constructor(
                String.class,
                clipMeta.broadDate
            ))
            .from(clipMeta)
            .where(clipMeta.programId.eq(programId))
            .where(clipMeta.broadDate.isNotNull())
            .groupBy(clipMeta.broadDate)
            .orderBy(clipMeta.broadDate.desc())
            .fetch();
    }

    @Override
    public List<ClipDTO> getProgramItems(Integer recommendProgramId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QWeekProgramItem weekProgramItem = QWeekProgramItem.weekProgramItem;
        QClipMeta clipMeta = QClipMeta.clipMeta;
        QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;

        return queryFactory.select(
                Projections.constructor(
                        ClipDTO.class,
                        clipMeta.clipMetaId,
                        JPAExpressions.select(clipMetaLang.clipTitle)
                            .from(clipMetaLang)
                            .where(clipMetaLang.langType.eq(LangType.KO.name()))
                            .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId)),
                        JPAExpressions.select(clipMetaLang.clipTitle)
                            .from(clipMetaLang)
                            .where(clipMetaLang.langType.eq(LangType.EN.name()))
                            .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId)),
                        clipMeta.broadDate,
                        clipMeta.enableYn
                ))
                .from(weekProgramItem)
                .leftJoin(weekProgramItem.clipMeta, clipMeta)
                .where(weekProgramItem.recommendProgramId.eq(recommendProgramId))
                .orderBy(clipMeta.clipMetaId.desc())
                .fetch();
    }

    @Override
    public List<ClipDTO> getProgramsByBroadDate(
            String programId,
            String broadDate,
            Integer recommendProgramId
    ) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;
        QWeekProgramItem weekProgramItem = QWeekProgramItem.weekProgramItem;
        QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;

        return queryFactory.select(
            Projections.constructor(
                ClipDTO.class,
                clipMeta.clipMetaId,
                JPAExpressions.select(clipMetaLang.clipTitle)
                    .from(clipMetaLang)
                    .where(clipMetaLang.langType.eq(LangType.KO.name()))
                    .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId)),
                JPAExpressions.select(clipMetaLang.clipTitle)
                    .from(clipMetaLang)
                    .where(clipMetaLang.langType.eq(LangType.EN.name()))
                    .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId)),
                clipMeta.broadDate,
                clipMeta.enableYn
            ))
            .from(clipMeta)
            .leftJoin(clipMeta.weekProgramItem, weekProgramItem)
            .where(clipMeta.programId.eq(programId))
            .where(clipMeta.broadDate.eq(broadDate))
            .where(weekProgramItem.clipMeta.isNull())
            .orderBy(clipMeta.clipMetaId.desc())
            .fetch();
    }
}

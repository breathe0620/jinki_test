package kr.mubeat.cms.repository.recommend.weekprogram.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.domain.meta.clip.QClipMeta;
import kr.mubeat.cms.domain.meta.program.QProgramMeta;
import kr.mubeat.cms.domain.recommend.weekprogram.QWeekProgram;
import kr.mubeat.cms.domain.recommend.weekprogram.WeekProgram;
import kr.mubeat.cms.dto.recommend.program.ProgramDTO;
import kr.mubeat.cms.repository.recommend.weekprogram.WeekProgramRepository;
import kr.mubeat.cms.repository.recommend.weekprogram.WeekProgramRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Repository
public class WeekProgramRepositoryImpl extends QueryDslRepositorySupport implements WeekProgramRepositoryCustom {

    public WeekProgramRepositoryImpl() { super(WeekProgram.class); }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public List<ProgramDTO> getPrograms() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QWeekProgram weekProgram = QWeekProgram.weekProgram;
        QProgramMeta programMeta = QProgramMeta.programMeta;

        return queryFactory.select(
            Projections.constructor(
                ProgramDTO.class,
                weekProgram.recommendProgramId,
                weekProgram.programId,
                programMeta.programTitle,
                programMeta.programTitleEn,
                weekProgram.modifyDt
            ))
            .from(weekProgram)
            .leftJoin(weekProgram.programMeta, programMeta)
            .fetch();
    }

    @Override
    public ProgramDTO getProgram(Integer recommendProgramId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QWeekProgram weekProgram = QWeekProgram.weekProgram;
        QProgramMeta programMeta = QProgramMeta.programMeta;

        return queryFactory.select(
                Projections.constructor(
                        ProgramDTO.class,
                        weekProgram.recommendProgramId,
                        weekProgram.programId,
                        programMeta.programTitle,
                        programMeta.programTitleEn,
                        weekProgram.bannerImgUrl,
                        weekProgram.modifyDt
                ))
                .from(weekProgram)
                .leftJoin(weekProgram.programMeta, programMeta)
                .where(weekProgram.recommendProgramId.eq(recommendProgramId))
                .fetchOne();
    }
}

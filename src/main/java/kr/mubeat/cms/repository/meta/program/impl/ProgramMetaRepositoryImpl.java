package kr.mubeat.cms.repository.meta.program.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.program.ProgramMeta;
import kr.mubeat.cms.domain.meta.program.QProgramMeta;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.dto.meta.program.ProgramMetaDTO;
import kr.mubeat.cms.repository.meta.program.ProgramMetaRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 21..
 */
@Repository
public class ProgramMetaRepositoryImpl extends QueryDslRepositorySupport implements ProgramMetaRepositoryCustom {

    public ProgramMetaRepositoryImpl() {
        super(ProgramMeta.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<ProgramMetaDTO> getProgramMetaList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QProgramMeta programMeta = QProgramMeta.programMeta;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getCorporator() != null && searchParam.getCorporator() != "") {
            booleanBuilder
                .and(programMeta.corporator.eq(searchParam.getCorporator()));
        }
        if (searchParam.getProgram() != null && searchParam.getProgram() != "") {
            booleanBuilder
                .and(programMeta.programId.eq(searchParam.getProgram()));
        }
        if (searchParam.getType() != null && searchParam.getType() == SearchType.SEARCH_BY_PROGRAM_NAME) {
            booleanBuilder
                .and(programMeta.programTitle.like('%' + searchParam.getSearchText() + '%'));
        }

        QueryResults<ProgramMetaDTO> result = queryFactory.select(
            Projections.constructor(
                ProgramMetaDTO.class,
                programMeta.programId,
                programMeta.corporator,
                programMeta.programTitle,
                programMeta.programTitleEn,
                programMeta.limitnation
            ))
            .from(programMeta)
            .where(booleanBuilder)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(programMeta.programId.desc()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<String> getCorporators() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QProgramMeta programMeta = QProgramMeta.programMeta;

        return queryFactory.select(
                Projections.constructor(
                    String.class,
                    programMeta.corporator
                ))
                .from(programMeta)
                .groupBy(programMeta.corporator).fetchResults().getResults();
    }

    @Override
    public List<ProgramSearchDTO> getPrograms(String corporator) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QProgramMeta programMeta = QProgramMeta.programMeta;

        return queryFactory.select(
                Projections.constructor(
                    ProgramSearchDTO.class,
                    programMeta.programId,
                    programMeta.programTitle
                ))
                .from(programMeta)
                .where(programMeta.corporator.eq(corporator))
                .groupBy(programMeta.programTitle).fetchResults().getResults();
    }

    @Override
    public List<ProgramMetaDTO> getProgramForExcel() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QProgramMeta programMeta = QProgramMeta.programMeta;

        return queryFactory.select(
            Projections.constructor(
                ProgramMetaDTO.class,
                programMeta.programId,
                programMeta.corporator,
                programMeta.programTitle,
                programMeta.programTitleEn,
                programMeta.limitnation
            ))
            .from(programMeta)
            .fetchResults().getResults();
    }

    @Override
    public Long updateProgramTitleEn(String programId, String programTitleEn) {

        QProgramMeta programMeta = QProgramMeta.programMeta;

        return new JPAUpdateClause(this.getEntityManager(), programMeta)
                .where(programMeta.programId.eq(programId))
                .set(programMeta.programTitleEn, programTitleEn)
                .execute();
    }
}

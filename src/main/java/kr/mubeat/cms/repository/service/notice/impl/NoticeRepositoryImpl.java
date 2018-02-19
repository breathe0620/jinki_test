package kr.mubeat.cms.repository.service.notice.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.service.notice.Notice;
import kr.mubeat.cms.domain.service.notice.QNotice;
import kr.mubeat.cms.domain.service.notice.QNoticeLang;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.service.notice.NoticeDetailDTO;
import kr.mubeat.cms.dto.service.notice.NoticeListDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.service.notice.NoticeRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 9. 27..
 */
@Repository
public class NoticeRepositoryImpl extends QueryDslRepositorySupport implements NoticeRepositoryCustom {

    public NoticeRepositoryImpl() {
        super(Notice.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<NoticeListDTO> getNoticeList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QNotice notice = QNotice.notice;
        QNoticeLang noticeLang = QNoticeLang.noticeLang;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_NOTICE_SUBJECT) {
                booleanBuilder
                    .and(noticeLang.subject.like('%'+searchParam.getSearchText()+'%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_NOTICE_CONTENT) {
                booleanBuilder
                    .and(noticeLang.content.like('%'+searchParam.getSearchText()+'%'));
            }
        }

        QueryResults<NoticeListDTO> result = queryFactory.select(
            Projections.constructor(
                NoticeListDTO.class,
                notice.noticeNo,
                notice.category,
                notice.isImportant,
                JPAExpressions.select(noticeLang.subject)
                    .from(noticeLang)
                    .where(noticeLang.noticeNo.eq(notice.noticeNo)
                        .and(noticeLang.langType.eq(LangType.KO.name()))),
                JPAExpressions.select(noticeLang.subject)
                    .from(noticeLang)
                    .where(noticeLang.noticeNo.eq(notice.noticeNo)
                        .and(noticeLang.langType.eq(LangType.EN.name()))),
                notice.regDt
            ))
            .from(notice)
            .leftJoin(notice.noticeLang, noticeLang)
            .where(booleanBuilder)
            .groupBy(notice.noticeNo)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(notice.isImportant.desc())
            .orderBy(notice.noticeNo.desc())
            .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public NoticeDetailDTO getNoticeDetail(Integer noticeNo, LangType langType) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QNotice notice = QNotice.notice;
        QNoticeLang noticeLang = QNoticeLang.noticeLang;

        return queryFactory.select(
            Projections.constructor(
                NoticeDetailDTO.class,
                notice.noticeNo,
                notice.category,
                notice.isImportant,
                noticeLang.langType,
                noticeLang.subject,
                noticeLang.content,
                notice.regDt
            ))
            .from(notice)
            .leftJoin(notice.noticeLang, noticeLang)
            .where(notice.noticeNo.eq(noticeNo))
            .where(noticeLang.langType.eq(langType.name())).fetchOne();
    }

    @Override
    public void deleteNotice(Integer noticeNo) {

        QNotice notice = QNotice.notice;
        QNoticeLang noticeLang = QNoticeLang.noticeLang;

        new JPADeleteClause(this.getEntityManager(), noticeLang)
            .where(noticeLang.noticeNo.eq(noticeNo)
                .and(noticeLang.langType.eq(LangType.KO.name())))
            .execute();
        new JPADeleteClause(this.getEntityManager(), noticeLang)
            .where(noticeLang.noticeNo.eq(noticeNo)
                .and(noticeLang.langType.eq(LangType.EN.name())))
            .execute();
        new JPADeleteClause(this.getEntityManager(), notice)
            .where(notice.noticeNo.eq(noticeNo))
            .execute();
    }

    @Override
    public void deleteNoticeLangType(Integer noticeNo, LangType langType) {

        QNoticeLang noticeLang = QNoticeLang.noticeLang;

        new JPADeleteClause(this.getEntityManager(), noticeLang)
            .where(noticeLang.noticeNo.eq(noticeNo)
            .and(noticeLang.langType.eq(langType.name())))
            .execute();
    }
}

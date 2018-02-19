package kr.mubeat.cms.repository.service.faq.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.service.faq.FAQ;
import kr.mubeat.cms.domain.service.faq.QFAQ;
import kr.mubeat.cms.domain.service.faq.QFAQLang;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.service.faq.FAQDetailDTO;
import kr.mubeat.cms.dto.service.faq.FAQListDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.service.faq.FAQRepositoryCustom;
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
public class FAQRepositoryImpl extends QueryDslRepositorySupport implements FAQRepositoryCustom {

    public FAQRepositoryImpl() {
        super(FAQ.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<FAQListDTO> getFAQList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QFAQ faq = QFAQ.fAQ;
        QFAQLang faqLang = QFAQLang.fAQLang;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_FAQ_SUBJECT) {
                booleanBuilder
                    .and(faqLang.subject.like('%'+searchParam.getSearchText()+'%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_FAQ_CONTENT) {
                booleanBuilder
                    .and(faqLang.content.like('%'+searchParam.getSearchText()+'%'));
            }
        }
        QueryResults<FAQListDTO> result = queryFactory.select(
            Projections.constructor(
                FAQListDTO.class,
                faq.faqNo,
                faq.category,
                faq.isImportant,
                JPAExpressions.select(faqLang.subject)
                    .from(faqLang)
                    .where(faqLang.faqNo.eq(faq.faqNo)
                        .and(faqLang.langType.eq(LangType.KO.name()))),
                JPAExpressions.select(faqLang.subject)
                    .from(faqLang)
                    .where(faqLang.faqNo.eq(faq.faqNo)
                        .and(faqLang.langType.eq(LangType.EN.name()))),
                faq.regDt
            ))
            .from(faq)
            .leftJoin(faq.faqLang, faqLang)
            .where(booleanBuilder)
            .groupBy(faq.faqNo)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(faq.isImportant.desc())
            .orderBy(faq.faqNo.desc())
            .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public FAQDetailDTO getFAQDetail(Integer faqNo, LangType langType) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QFAQ faq = QFAQ.fAQ;
        QFAQLang faqLang = QFAQLang.fAQLang;

        return queryFactory.select(
            Projections.constructor(
                FAQDetailDTO.class,
                faq.faqNo,
                faq.category,
                faq.isImportant,
                faqLang.langType,
                faqLang.subject,
                faqLang.content,
                faq.regDt
            ))
            .from(faq)
            .leftJoin(faq.faqLang, faqLang)
            .where(faq.faqNo.eq(faqNo))
            .where(faqLang.langType.eq(langType.name())).fetchOne();
    }

    @Override
    public void deleteFAQ(Integer faqNo) {

        QFAQ faq = QFAQ.fAQ;
        QFAQLang faqLang = QFAQLang.fAQLang;

        new JPADeleteClause(this.getEntityManager(), faqLang)
            .where(faqLang.faqNo.eq(faqNo)
                .and(faqLang.langType.eq(LangType.KO.name())))
            .execute();
        new JPADeleteClause(this.getEntityManager(), faqLang)
            .where(faqLang.faqNo.eq(faqNo)
                .and(faqLang.langType.eq(LangType.EN.name())))
            .execute();
        new JPADeleteClause(this.getEntityManager(), faq)
            .where(faq.faqNo.eq(faqNo))
            .execute();
    }

    @Override
    public void deleteFAQLangType(Integer faqNo, LangType langType) {

        QFAQLang faqLang = QFAQLang.fAQLang;

        new JPADeleteClause(this.getEntityManager(), faqLang)
            .where(faqLang.faqNo.eq(faqNo)
            .and(faqLang.langType.eq(langType.name())))
            .execute();
    }
}

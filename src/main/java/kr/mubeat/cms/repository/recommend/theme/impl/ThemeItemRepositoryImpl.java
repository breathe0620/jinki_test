package kr.mubeat.cms.repository.recommend.theme.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.clip.QClipMeta;
import kr.mubeat.cms.domain.meta.clip.QClipMetaLang;
import kr.mubeat.cms.domain.recommend.theme.QThemeItem;
import kr.mubeat.cms.domain.recommend.theme.ThemeItem;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.recommend.theme.ClipDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.recommend.theme.ThemeItemRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 11. 7..
 */
@Repository
public class ThemeItemRepositoryImpl extends QueryDslRepositorySupport implements ThemeItemRepositoryCustom {

    public ThemeItemRepositoryImpl() { super(ThemeItem.class); }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<ClipDTO> getThemeItems(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        if (searchParam.getType() != null
            &&
            searchParam.getType() == SearchType.SEARCH_BY_THEME_ID
            &&
            searchParam.getSearchText() != null
            &&
            !searchParam.getSearchText().equalsIgnoreCase("")
        ) {
            JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

            QThemeItem themeItem = QThemeItem.themeItem;
            QClipMeta clipMeta = QClipMeta.clipMeta;
            QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;

            QueryResults<ClipDTO> result = queryFactory.select(
                Projections.constructor(
                    ClipDTO.class,
                    themeItem.clipMetaId,
                    JPAExpressions.select(clipMetaLang.clipTitle)
                        .from(clipMetaLang)
                        .where(clipMetaLang.langType.eq(LangType.KO.name()))
                        .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId)),
                    JPAExpressions.select(clipMetaLang.clipTitle)
                        .from(clipMetaLang)
                        .where(clipMetaLang.langType.eq(LangType.EN.name()))
                        .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId)),
                    clipMeta.enableYn
                ))
                .from(themeItem)
                .leftJoin(themeItem.clipMeta, clipMeta)
                .where(themeItem.recommendThemeId.eq(Integer.parseInt(searchParam.getSearchText())))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(themeItem.regDt.desc())
                .fetchResults();

            return new PageImpl<>(result.getResults(), pageable, result.getTotal());
        } else {
            return null;
        }
    }

    @Override
    public Long deleteByThemeId(Integer recommendThemeId) {

        QThemeItem themeItem = QThemeItem.themeItem;

        return new JPADeleteClause(this.getEntityManager(), themeItem)
                .where(themeItem.recommendThemeId.eq(recommendThemeId))
                .execute();
    }
}

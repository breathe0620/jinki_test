package kr.mubeat.cms.repository.recommend.theme.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import kr.mubeat.cms.domain.recommend.theme.QTheme;
import kr.mubeat.cms.domain.recommend.theme.Theme;
import kr.mubeat.cms.dto.recommend.theme.ThemeDTO;
import kr.mubeat.cms.repository.recommend.theme.ThemeRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Repository
public class ThemeRepositoryImpl extends QueryDslRepositorySupport implements ThemeRepositoryCustom {

    public ThemeRepositoryImpl() { super(Theme.class); }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public List<ThemeDTO> getThemes() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QTheme theme = QTheme.theme;

        return queryFactory.select(
            Projections.constructor(
                ThemeDTO.class,
                theme.recommendThemeId,
                theme.position,
                theme.title,
                theme.titleEn,
                theme.themeImgUrl,
                theme.enableYn,
                theme.createDt,
                theme.modifyDt
            ))
            .from(theme)
            .orderBy(theme.position.desc())
            .fetch();
    }

    @Override
    public Long updatePosition(Integer recommendThemeId, Integer position) {
        QTheme theme = QTheme.theme;

        return new JPAUpdateClause(this.getEntityManager(), theme)
                    .where(theme.recommendThemeId.eq(recommendThemeId))
                    .set(theme.position, position)
                    .execute();
    }
}

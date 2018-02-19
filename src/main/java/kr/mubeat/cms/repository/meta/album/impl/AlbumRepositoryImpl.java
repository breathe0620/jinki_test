package kr.mubeat.cms.repository.meta.album.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.album.Album;
import kr.mubeat.cms.domain.meta.album.QAlbum;
import kr.mubeat.cms.domain.meta.album.QAlbumArtist;
import kr.mubeat.cms.domain.meta.album.QAlbumLang;
import kr.mubeat.cms.domain.meta.artist.QArtist;
import kr.mubeat.cms.domain.meta.artist.QArtistLang;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.album.AlbumDTO;
import kr.mubeat.cms.dto.meta.album.AlbumExcelDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.meta.album.AlbumRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 9..
 */
@Repository
public class AlbumRepositoryImpl extends QueryDslRepositorySupport implements AlbumRepositoryCustom {

    public AlbumRepositoryImpl() {
        super(Album.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public Page<AlbumDTO> getAlbumList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbum album = QAlbum.album;
        QAlbumLang albumLang = QAlbumLang.albumLang;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QAlbumArtist albumArtist = QAlbumArtist.albumArtist;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getStart() != null) {
            booleanBuilder
                    .and(album.publishDt.gt(searchParam.getStart()));
        }
        if (searchParam.getEnd() != null) {
            booleanBuilder
                    .and(album.publishDt.lt(searchParam.getEnd()));
        }
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_ALBUM_NAME) {
                booleanBuilder
                        .and(albumLang.albumTitle.like('%' + searchParam.getSearchText() + '%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_ARTIST_NAME) {
                booleanBuilder
                        .and(albumArtist.artistId.in(
                                JPAExpressions
                                        .select(artist.artistId)
                                        .from(artist)
                                        .where(artistLang.artistName.like('%' + searchParam.getSearchText() + '%'))
                                )
                        );
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_ORIGIN_ID) {
                booleanBuilder
                        .and(album.originId.eq(Long.parseLong(searchParam.getSearchText())));
            }
        }

        QueryResults<AlbumDTO> result = queryFactory.select(
                Projections.constructor(
                        AlbumDTO.class,
                        album.albumId,
                        album.originId,
                        albumLang.albumTitle,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
                        album.publishDt))
                .from(album)
                .where(album.deleteYn.eq(Constants.FLAG_N))
                .leftJoin(album.albumLang, albumLang)
                    .on(albumLang.langType.eq(LangType.KO.name()))
                .leftJoin(album.albumArtist, albumArtist)
                .leftJoin(albumArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .where(booleanBuilder)
                .groupBy(album.albumId)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(album.createDt.desc()).fetchResults();

//        QueryResults<AlbumDTO> result = queryFactory.select(
//                Projections.constructor(
//                        AlbumDTO.class,
//                        album.albumId,
//                        album.originId,
////                        albumLang.albumTitle,
//                        JPAExpressions.select(albumLang.albumTitle)
//                            .from(albumLang)
//                            .where(albumLang.albumId.eq(album.albumId)
//                                    .and(albumLang.langType.eq(LangType.KO.name()))),
//                        JPAExpressions.select(albumLang.albumTitle)
//                                .from(albumLang)
//                                .where(albumLang.albumId.eq(album.albumId)
//                                        .and(albumLang.langType.eq(LangType.EN.name()))),
//                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
//                        album.publishDt))
//                .from(album)
//                .where(album.deleteYn.eq(Constants.FLAG_N))
//                .leftJoin(album.albumLang, albumLang)
//                .leftJoin(album.albumArtist, albumArtist)
//                .leftJoin(albumArtist.artist, artist)
//                .leftJoin(artist.artistLang, artistLang)
//                    .on(artistLang.langType.eq(LangType.KO.name()))
//                .where(booleanBuilder)
//                .groupBy(album.albumId)
//                .limit(pageable.getPageSize())
//                .offset(pageable.getOffset())
//                .orderBy(album.createDt.desc()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public AlbumDTO getAlbumDetail(Long albumId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbum album = QAlbum.album;
        QAlbumLang albumLang = QAlbumLang.albumLang;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QAlbumArtist albumArtist = QAlbumArtist.albumArtist;

        return queryFactory.select(
                Projections.constructor(
                        AlbumDTO.class,
                        album.albumId,
                        album.originId,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artist.artistId),
                        artist.originId,
                        JPAExpressions.select(albumLang.albumTitle)
                            .from(albumLang)
                            .where(albumLang.albumId.eq(albumId))
                            .where(albumLang.langType.eq(LangType.KO.name())),
                        JPAExpressions.select(albumLang.albumTitle)
                            .from(albumLang)
                            .where(albumLang.albumId.eq(albumId))
                            .where(albumLang.langType.eq(LangType.EN.name())),
                        album.albumTypeId,
                        album.albumImgUrl,
                        album.createDt,
                        album.publishDt,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName)
                ))
                .from(album)
                .where(album.deleteYn.eq(Constants.FLAG_N))
                .leftJoin(album.albumArtist, albumArtist)
                .leftJoin(albumArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .where(album.albumId.eq(albumId))
                .groupBy(album.albumId)
                .fetchOne();
    }

    @Override
    public List<AlbumDTO> getAllAlbumForElasticSearch(int page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbum album = QAlbum.album;
        QAlbumLang albumLang = QAlbumLang.albumLang;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QAlbumArtist albumArtist = QAlbumArtist.albumArtist;

        return queryFactory.select(
            Projections.constructor(
                AlbumDTO.class,
                album.albumId,
                album.originId,
                albumLang.albumTitle,
                Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
                album.publishDt
            ))
            .from(album)
            .where(album.deleteYn.eq(Constants.FLAG_N))
            .leftJoin(album.albumLang, albumLang)
            .leftJoin(album.albumArtist, albumArtist)
                .on(artistLang.langType.eq(LangType.KO.name()))
            .leftJoin(albumArtist.artist, artist)
            .leftJoin(artist.artistLang, artistLang)
                .on(albumLang.langType.eq(LangType.KO.name()))
            .groupBy(album.albumId)
            .limit(1000)
            .offset(page)
            .orderBy(album.albumId.asc())
            .fetchResults().getResults();
    }

    @Override
    public List<Long> getAllDeletedAlbumForElasticSearch(int page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbum album = QAlbum.album;

        return queryFactory.select(
                Projections.constructor(
                        Long.class,
                        album.albumId
                ))
                .from(album)
                .where(album.deleteYn.eq(Constants.FLAG_Y))
                .limit(1000)
                .offset(page)
                .orderBy(album.albumId.asc())
                .fetchResults().getResults();
    }

    @Override
    public String getAlbumArtists(Long albumId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbum album = QAlbum.album;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QAlbumArtist albumArtist = QAlbumArtist.albumArtist;

        return queryFactory.select(
                Projections.constructor(
                        String.class,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName)
                ))
                .from(album)
                .where(album.deleteYn.eq(Constants.FLAG_N))
                .where(album.albumId.eq(albumId))
                .leftJoin(album.albumArtist, albumArtist)
                .leftJoin(albumArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .groupBy(album.albumId)
                .fetchOne();
    }

    @Override
    public String getAlbumArtistsEn(Long albumId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbum album = QAlbum.album;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QAlbumArtist albumArtist = QAlbumArtist.albumArtist;

        return queryFactory.select(
                Projections.constructor(
                        String.class,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName)
                ))
                .from(album)
                .where(album.deleteYn.eq(Constants.FLAG_N))
                .where(album.albumId.eq(albumId))
                .leftJoin(album.albumArtist, albumArtist)
                .leftJoin(albumArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                .on(artistLang.langType.eq(LangType.EN.name()))
                .groupBy(album.albumId)
                .fetchOne();
    }

    @Override
    public List<AlbumExcelDTO> getAlbumForExcel() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAlbum album = QAlbum.album;
        QAlbumLang albumLang = QAlbumLang.albumLang;
        QAlbumArtist albumArtist = QAlbumArtist.albumArtist;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;

        return queryFactory.select(
                Projections.constructor(
                        AlbumExcelDTO.class,
                        album.albumId,
                        album.originId,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
                        JPAExpressions.select(albumLang.albumTitle)
                            .from(albumLang)
                            .where(albumLang.albumId.eq(album.albumId))
                            .where(albumLang.langType.eq(LangType.KO.name())),
                        JPAExpressions.select(albumLang.albumTitle)
                            .from(albumLang)
                            .where(albumLang.albumId.eq(album.albumId))
                            .where(albumLang.langType.eq(LangType.EN.name())),
                        album.publishDt,
                        album.createDt,
                        album.deleteYn
                ))
                .from(album)
                .leftJoin(album.albumArtist, albumArtist)
                .leftJoin(albumArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .groupBy(album.albumId)
                .fetchResults().getResults();
    }
}

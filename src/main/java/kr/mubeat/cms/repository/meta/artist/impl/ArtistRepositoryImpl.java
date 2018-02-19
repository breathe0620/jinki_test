package kr.mubeat.cms.repository.meta.artist.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.agency.QAgency;
import kr.mubeat.cms.domain.meta.artist.*;
import kr.mubeat.cms.domain.meta.song.QSong;
import kr.mubeat.cms.domain.meta.song.QSongLang;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import kr.mubeat.cms.dto.meta.artist.ArtistExcelDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.meta.artist.ArtistRepositoryCustom;
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
 * Created by doohwan.yoo on 2017. 5. 15..
 */
@Repository
public class ArtistRepositoryImpl extends QueryDslRepositorySupport implements ArtistRepositoryCustom {

    public ArtistRepositoryImpl() {
        super(Artist.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public Page<ArtistDTO> getArtistList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;

        /**
         * 아티스트명 / 곡mid
         */
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getStart() != null) {
            booleanBuilder
                    .and(artist.createDt.gt(searchParam.getStart()));
        }
        if (searchParam.getEnd() != null) {
            booleanBuilder
                    .and(artist.createDt.lt(searchParam.getEnd()));
        }
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_ARTIST_NAME) {
                booleanBuilder
                        .and(artistLang.artistName.like('%' + searchParam.getSearchText()+'%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_ORIGIN_ID) {
                booleanBuilder
                        .and(artist.originId.eq(Long.parseLong(searchParam.getSearchText())));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_AGENCY_ID) {
                booleanBuilder
                        .and(artist.agencyId.eq(Integer.parseInt(searchParam.getSearchText())));
            }
        }

//        QueryResults<ArtistDTO> result = queryFactory.select(
//            Projections.constructor(
//                ArtistDTO.class,
//                artist.artistId,
//                artist.originId,
//                artistLang.artistName,
//                artist.createDt))
//                .from(artist)
//                .leftJoin(artist.artistLang, artistLang)
//                    .on(artistLang.langType.eq(LangType.KO.name()))
//                .where(artist.deleteYn.eq(Constants.FLAG_N))
//                .where(booleanBuilder)
//                .limit(pageable.getPageSize())
//                .offset(pageable.getOffset())
//                .orderBy(artist.createDt.desc()).fetchResults();

                QueryResults<ArtistDTO> result = queryFactory.select(
                        Projections.constructor(
                                ArtistDTO.class,
                                artist.artistId,
                                artist.originId,
//                                artistLang.artistName,
                                JPAExpressions.select(artistLang.artistName)
                                        .from(artistLang)
                                        .where(artistLang.artistId.eq(artist.artistId)
                                                .and(artistLang.langType.eq(LangType.KO.name()))),
                                JPAExpressions.select(artistLang.artistName)
                                        .from(artistLang)
                                        .where(artistLang.artistId.eq(artist.artistId)
                                                .and(artistLang.langType.eq(LangType.EN.name()))),
                                artist.createDt))
                        .from(artist)
                        .leftJoin(artist.artistLang, artistLang)
                        .on(artistLang.langType.eq(LangType.KO.name()))
                        .where(artist.deleteYn.eq(Constants.FLAG_N))
                        .where(booleanBuilder)
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .orderBy(artist.createDt.desc()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public ArtistDTO getArtistDetail(Long artistId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QArtistType artistType = QArtistType.artistType;
        QAgency agency = QAgency.agency;
        QSong song = QSong.song;
        QSongLang songLang = QSongLang.songLang;
        QMemberArtist memberArtist = QMemberArtist.memberArtist;
        QRelativeArtist relativeArtist = QRelativeArtist.relativeArtist;
        QSimilarArtist similarArtist = QSimilarArtist.similarArtist;
        QArtistGroup artistGroup = QArtistGroup.artistGroup;

        ArtistDTO result = queryFactory.select(
                Projections.constructor(
                        ArtistDTO.class,
                        artist.artistId,
                        artist.originId,
                        JPAExpressions.select(artistLang.artistName)
                            .from(artistLang)
                            .where(artistLang.artistId.eq(artistId))
                            .where(artistLang.langType.eq(LangType.KO.name())),
                        JPAExpressions.select(artistLang.artistName)
                            .from(artistLang)
                            .where(artistLang.artistId.eq(artistId))
                            .where(artistLang.langType.eq(LangType.EN.name())),
                        artist.artistTypeId,
                        artistType.typeName,
                        artist.debutDt,
                        artist.debutSongId,
                        JPAExpressions.select(songLang.songName)
                            .from(songLang)
                            .where(songLang.songId.eq(song.songId))
                            .where(songLang.langType.eq(LangType.KO.name())),
                        artist.organizeDt,
                        artist.agencyId,
                        agency.agencyName,
                        artist.artistMainImgUrl,
                        artist.artistProfileImgUrl,
                        artist.createDt,
                        artist.modifyDt,
                        artist.birthDt
                ))
                .from(artist)
                .leftJoin(artist.artistType, artistType)
                .leftJoin(artist.agency, agency)
                .leftJoin(artist.song, song)
                .where(artist.artistId.eq(artistId))
                .where(artist.deleteYn.eq(Constants.FLAG_N))
                .fetchOne();

        if(result != null) {
            result.setMemberArtist(
                queryFactory.select(
                    Projections.constructor(
                        MemberArtist.class,
                        memberArtist.artistId,
                        memberArtist.memberArtistId,
                        artistLang.artistName
                    ))
                    .from(memberArtist)
                    .innerJoin(memberArtist.artist, artist)
                    .leftJoin(artist.artistLang, artistLang)
                        .on(artistLang.langType.eq(LangType.KO.name()))
                    .where(memberArtist.artistId.eq(artistId))
                    .fetch());

            result.setRelativeArtist(
                queryFactory.select(
                    Projections.constructor(
                        RelativeArtist.class,
                        relativeArtist.artistId,
                        relativeArtist.relativeArtistId,
                        artistLang.artistName
                    ))
                    .from(relativeArtist)
                    .innerJoin(relativeArtist.artist, artist)
                    .leftJoin(artist.artistLang, artistLang)
                        .on(artistLang.langType.eq(LangType.KO.name()))
                    .where(relativeArtist.artistId.eq(artistId))
                    .fetch());

            result.setSimilarArtist(
                queryFactory.select(
                    Projections.constructor(
                        SimilarArtist.class,
                        similarArtist.artistId,
                        similarArtist.similarArtistId,
                        artistLang.artistName
                    ))
                    .from(similarArtist)
                    .innerJoin(similarArtist.artist, artist)
                    .leftJoin(artist.artistLang, artistLang)
                        .on(artistLang.langType.eq(LangType.KO.name()))
                    .where(similarArtist.artistId.eq(artistId))
                    .fetch());

            result.setArtistGroup(
                queryFactory.select(
                    Projections.constructor(
                        ArtistGroup.class,
                        artistGroup.artistId,
                        artistGroup.artistGroupId,
                        artistLang.artistName
                    ))
                    .from(artistGroup)
                    .innerJoin(artistGroup.artist, artist)
                    .leftJoin(artist.artistLang, artistLang)
                        .on(artistLang.langType.eq(LangType.KO.name()))
                    .where(artistGroup.artistId.eq(artistId))
                    .fetch());
        }

        return result;
    }

    @Override
    public Long removeAgencyByAgencyId(Integer agencyId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QArtist artist = QArtist.artist;

        return queryFactory.update(artist)
                .set(artist.agencyId, 0)
                .where(artist.agencyId.eq(agencyId))
                .execute();
    }

    @Override
    public List<ArtistDTO> findSameAgencyArtist(Integer agencyId) {

        // ToDo 추후 페이징이 필요할 여지가 있음
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;

        return queryFactory.select(
                Projections.constructor(
                    ArtistDTO.class,
                    artist.artistId,
                    artist.originId,
                    artistLang.artistName
                ))
                .from(artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .where(artist.deleteYn.eq(Constants.FLAG_N))
                .where(artist.agencyId.eq(agencyId)).fetch();

    }

    @Override
    public void deleteArtistDeltailSubinfo(Long artistId, List<Long> artistGroupList, List<Long> relativeArtistList, List<Long> similarArtistList) {
        QMemberArtist memberArtist = QMemberArtist.memberArtist;
        QRelativeArtist relativeArtist = QRelativeArtist.relativeArtist;
        QSimilarArtist similarArtist = QSimilarArtist.similarArtist;
        QArtistGroup artistGroup = QArtistGroup.artistGroup;
        QArtistActiveages artistActiveages = QArtistActiveages.artistActiveages;

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());
//        queryFactory.delete(memberArtist).where(memberArtist.artistId.eq(artistId)).execute();
        queryFactory.delete(relativeArtist).where(relativeArtist.artistId.eq(artistId)).execute();
        queryFactory.delete(similarArtist).where(similarArtist.artistId.eq(artistId)).execute();
        queryFactory.delete(artistGroup).where(artistGroup.artistId.eq(artistId)).execute();
        queryFactory.delete(artistActiveages).where(artistActiveages.artistId.eq(artistId)).execute();

        queryFactory.delete(memberArtist)
                .where(memberArtist.artistId.notIn(artistGroupList))
                .where(memberArtist.memberArtistId.eq(artistId)).execute();
        queryFactory.delete(relativeArtist)
                .where(relativeArtist.artistId.notIn(relativeArtistList))
                .where(relativeArtist.relativeArtistId.eq(artistId)).execute();
        queryFactory.delete(similarArtist)
                .where(similarArtist.artistId.notIn(similarArtistList))
                .where(similarArtist.similarArtistId.eq(artistId)).execute();

    }

    @Override
    public List<ArtistDTO> getAllArtistForElasticSearch(int page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;

        return queryFactory.select(
                Projections.constructor(
                        ArtistDTO.class,
                        artist.artistId,
                        artist.originId,
                        artistLang.artistName,
                        artist.createDt
                ))
                .from(artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .where(artist.deleteYn.eq(Constants.FLAG_N))
                .limit(1000)
                .offset(page)
                .orderBy(artist.artistId.asc())
                .fetchResults().getResults();
    }

    @Override
    public List<Long> getAllDeletedArtistForElasticSearch(int page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QArtist artist = QArtist.artist;

        return queryFactory.select(
                Projections.constructor(
                        Long.class,
                        artist.artistId
                ))
                .from(artist)
                .where(artist.deleteYn.eq(Constants.FLAG_Y))
                .limit(1000)
                .offset(page)
                .orderBy(artist.artistId.asc())
                .fetchResults().getResults();
    }

    @Override
    public List<ArtistExcelDTO> getArtistForExcel() {

        Query query = this.getEntityManager().createNativeQuery(
                "SELECT " +
                            "a.artistid, " +
                            "a.originid, " +
                            "GROUP_CONCAT( " +
                            "(SELECT artist.artistname " +
                            "FROM tbl_artist_lang AS artist " +
                            "WHERE artist.artistid=g.artistgroupid " +
                            "AND artist.langtype='KO')) AS 'group', " +
                            "GROUP_CONCAT( " +
                            "(SELECT artist.artistname " +
                            "FROM tbl_artist_lang AS artist " +
                            "WHERE artist.artistid=g.artistgroupid " +
                            "AND artist.langtype='EN')) AS 'groupen', " +
                            "(SELECT artist.artistname " +
                            "FROM tbl_artist_lang AS artist " +
                            "WHERE artist.artistid=a.artistid " +
                            "AND artist.langtype='KO') AS 'artistname', " +
                            "(SELECT artist.artistname " +
                            "FROM tbl_artist_lang AS artist " +
                            "WHERE artist.artistid=a.artistid " +
                            "AND artist.langtype='EN') AS 'artistnameen', " +
                            "a.createdt, " +
                            "a.birthdt, " +
                            "a.deleteyn " +
                        "FROM tbl_artist a " +
                        "LEFT OUTER JOIN tbl_artist_group g " +
                        "ON a.artistid = g.artistid " +
                        "GROUP BY a.artistid;",
                "ArtistExcelDTO"
        );

        return query.getResultList();
    }
}

package kr.mubeat.cms.repository.meta.song.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.album.QAlbum;
import kr.mubeat.cms.domain.meta.album.QAlbumLang;
import kr.mubeat.cms.domain.meta.artist.QArtist;
import kr.mubeat.cms.domain.meta.artist.QArtistLang;
import kr.mubeat.cms.domain.meta.song.QSong;
import kr.mubeat.cms.domain.meta.song.QSongArtist;
import kr.mubeat.cms.domain.meta.song.QSongLang;
import kr.mubeat.cms.domain.meta.song.Song;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.song.SongDTO;
import kr.mubeat.cms.dto.meta.song.SongExcelDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.meta.song.SongRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
@Repository
public class SongRepositoryImpl extends QueryDslRepositorySupport implements SongRepositoryCustom {

    public SongRepositoryImpl() {
        super(Song.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public Page<SongDTO> getSongList(CustomSearchParam searchParam) {

        Pageable pageable =  new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;
        QSongLang songLang = QSongLang.songLang;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QSongArtist songArtist = QSongArtist.songArtist;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getStart() != null) {
            booleanBuilder
                    .and(song.createDt.gt(searchParam.getStart()));
        }
        if (searchParam.getEnd() != null) {
            booleanBuilder
                    .and(song.createDt.lt(searchParam.getEnd()));
        }
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_ARTIST_NAME) {
                booleanBuilder
                        .and(songArtist.artistId.in(
                                JPAExpressions
                                    .select(artist.artistId)
                                    .from(artist)
                                    .where(artistLang.artistName.like('%' + searchParam.getSearchText()+'%'))
                                )
                        );
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_SONG_NAME) {
                booleanBuilder
                        .and(songLang.songName.like('%' + searchParam.getSearchText()+'%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_ORIGIN_ID) {
                booleanBuilder
                        .and(song.originId.eq(Long.parseLong(searchParam.getSearchText())));
            }
        }

        QueryResults<SongDTO> result = queryFactory.select(
                Projections.constructor(
                        SongDTO.class,
                        song.songId,
                        song.originId,
                        songLang.songName,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
                        song.createDt
                        ))
                .from(song)
                .where(song.deleteYn.eq(Constants.FLAG_N))
                .leftJoin(song.songLang, songLang)
                    .on(songLang.langType.eq(LangType.KO.name()))
                .leftJoin(song.songArtist, songArtist)
                .leftJoin(songArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .where(booleanBuilder)
                .groupBy(song.songId)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(song.createDt.desc()).fetchResults();

//        QueryResults<SongDTO> result = queryFactory.select(
//                Projections.constructor(
//                        SongDTO.class,
//                        song.songId,
//                        song.originId,
//                        JPAExpressions.select(songLang.songName)
//                            .from(songLang)
//                            .where(songLang.songId.eq(song.songId)
//                                .and(songLang.langType.eq(LangType.KO.name()))),
//                        JPAExpressions.select(songLang.songName)
//                            .from(songLang)
//                            .where(songLang.songId.eq(song.songId)
//                                    .and(songLang.langType.eq(LangType.EN.name()))),
//                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
//                        song.createDt
//                ))
//                .from(song)
//                .where(song.deleteYn.eq(Constants.FLAG_N))
//                .leftJoin(song.songLang, songLang)
//                .leftJoin(song.songArtist, songArtist)
//                .leftJoin(songArtist.artist, artist)
//                .leftJoin(artist.artistLang, artistLang)
//                .on(artistLang.langType.eq(LangType.KO.name()))
//                .where(booleanBuilder)
//                .groupBy(song.songId)
//                .limit(pageable.getPageSize())
//                .offset(pageable.getOffset())
//                .orderBy(song.createDt.desc()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public SongDTO getSongDetail(Long songId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;
        QSongLang songLang = QSongLang.songLang;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QAlbum album = QAlbum.album;
        QAlbumLang albumLang = QAlbumLang.albumLang;
        QSongArtist songArtist = QSongArtist.songArtist;

        return queryFactory.select(
                Projections.constructor(
                        SongDTO.class,
                        song.songId,
                        song.originId,
                        JPAExpressions.select(songLang.songName)
                            .from(songLang)
                            .where(songLang.songId.eq(song.songId))
                            .where(songLang.langType.eq(LangType.KO.name())),
                        JPAExpressions.select(songLang.songName)
                            .from(songLang)
                            .where(songLang.songId.eq(song.songId))
                            .where(songLang.langType.eq(LangType.EN.name())),
                        song.genreId,
                        song.createDt,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artist.artistId),
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
                        song.albumId,
                        album.originId,
                        JPAExpressions.select(albumLang.albumTitle)
                            .from(albumLang)
                            .where(albumLang.albumId.eq(song.albumId))
                            .where(albumLang.langType.eq(LangType.KO.name())),
                        album.publishDt,
                        album.albumImgUrl,
                        song.lyric))
                .from(song)
                .where(song.deleteYn.eq(Constants.FLAG_N))
                .leftJoin(song.songArtist, songArtist)
                .leftJoin(songArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .leftJoin(song.album, album)
                .where(song.songId.eq(songId))
                .groupBy(song.songId)
                .fetchOne();
    }

    @Override
    public Long updateSongInfo(Long songId, Song param) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;

        return queryFactory.update(song).where(song.songId.eq(songId))
                .set(song.genreId, param.getGenreId())
                .set(song.originId, param.getOriginId())
                .set(song.albumId, param.getAlbumId())
                .set(song.lyric, param.getLyric())
                .execute();
    }

    @Override
    public List<SongDTO> getAllSongForElasticSearch(int page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;
        QSongLang songLang = QSongLang.songLang;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;
        QSongArtist songArtist = QSongArtist.songArtist;

        return queryFactory.select(
                Projections.constructor(
                        SongDTO.class,
                        song.songId,
                        song.originId,
                        songLang.songName,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
                        song.createDt
                ))
                .from(song)
                .where(song.deleteYn.eq(Constants.FLAG_N))
                .leftJoin(song.songLang, songLang)
                    .on(songLang.langType.eq(LangType.KO.name()))
                .leftJoin(song.songArtist, songArtist)
                .leftJoin(songArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .groupBy(song.songId)
                .limit(1000)
                .offset(page)
                .orderBy(song.songId.asc())
                .fetchResults().getResults();
    }

    @Override
    public List<Long> getAllDeletedSongForElasticSearch(int page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;

        return queryFactory.select(
                Projections.constructor(
                        Long.class,
                        song.songId
                ))
                .from(song)
                .where(song.deleteYn.eq(Constants.FLAG_Y))
                .limit(1000)
                .offset(page)
                .orderBy(song.songId.asc())
                .fetchResults().getResults();
    }

    @Override
    public String getSongArtists(Long songId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;
        QSongArtist songArtist = QSongArtist.songArtist;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;

        return queryFactory.select(
                Projections.constructor(
                        String.class,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName)
                ))
                .from(song)
                .where(song.deleteYn.eq(Constants.FLAG_N))
                .where(song.songId.eq(songId))
                .leftJoin(song.songArtist, songArtist)
                .leftJoin(songArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .groupBy(song.songId)
                .fetchOne();
    }

    @Override
    public String getSongArtistsEn(Long songId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;
        QSongArtist songArtist = QSongArtist.songArtist;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;

        return queryFactory.select(
                Projections.constructor(
                        String.class,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName)
                ))
                .from(song)
                .where(song.deleteYn.eq(Constants.FLAG_N))
                .where(song.songId.eq(songId))
                .leftJoin(song.songArtist, songArtist)
                .leftJoin(songArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                .on(artistLang.langType.eq(LangType.EN.name()))
                .groupBy(song.songId)
                .fetchOne();
    }

    @Override
    public List<SongExcelDTO> getSongForExcel() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QSong song = QSong.song;
        QSongLang songLang = QSongLang.songLang;
        QAlbum album = QAlbum.album;
        QAlbumLang albumLang = QAlbumLang.albumLang;
        QSongArtist songArtist = QSongArtist.songArtist;
        QArtist artist = QArtist.artist;
        QArtistLang artistLang = QArtistLang.artistLang;

        return queryFactory.select(
                Projections.constructor(
                        SongExcelDTO.class,
                        song.songId,
                        song.originId,
                        JPAExpressions.select(songLang.songName)
                            .from(songLang)
                            .where(songLang.songId.eq(song.songId))
                            .where(songLang.langType.eq(LangType.KO.name())),
                        JPAExpressions.select(songLang.songName)
                            .from(songLang)
                            .where(songLang.songId.eq(song.songId))
                            .where(songLang.langType.eq(LangType.EN.name())),
                        albumLang.albumTitle,
                        Expressions.stringTemplate("FUNCTION('GROUP_CONCAT', {0})", artistLang.artistName),
                        song.createDt,
                        song.deleteYn
                ))
                .from(song)
                .leftJoin(song.album, album)
                .leftJoin(album.albumLang, albumLang)
                    .on(albumLang.langType.eq(LangType.KO.name()))
                .leftJoin(song.songArtist, songArtist)
                .leftJoin(songArtist.artist, artist)
                .leftJoin(artist.artistLang, artistLang)
                    .on(artistLang.langType.eq(LangType.KO.name()))
                .groupBy(song.songId)
                .fetchResults().getResults();
    }
}

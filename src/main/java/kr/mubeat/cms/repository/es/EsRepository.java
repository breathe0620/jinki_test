package kr.mubeat.cms.repository.es;

import kr.mubeat.cms.enumerations.es.EsMetaType;
import kr.mubeat.cms.enumerations.es.EsOrder;
import kr.mubeat.cms.enumerations.es.EsSearchType;
import kr.mubeat.cms.domain.es.AlbumMeta;
import kr.mubeat.cms.domain.es.ArtistMeta;
import kr.mubeat.cms.domain.es.EsResult;
import kr.mubeat.cms.domain.es.SongMeta;
import kr.mubeat.cms.util.ElasticsearchUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 22..
 */
@Repository
public class EsRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ElasticsearchUtil elasticsearchUtil;

    @Autowired
    public EsRepository(
            ElasticsearchUtil elasticsearchUtil
    ) {
        this.elasticsearchUtil = elasticsearchUtil;
    }

    /**
     * Kibana 의 Console Search 로는 다음과 같이 표현 할 수 있다.
     GET /meta/album/_search
     {
        "query": {
            "match_phrase_prefix": {
                "name" : prefix
            }
        }
     }
     * @param prefix
     * @return
     */
    public EsResult findAlbumMetaByPrefix(String prefix) {
        EsResult result = new EsResult();

        JSONObject hits = elasticsearchUtil.searchItem(
                0,
                "album",
                prefix
        );

        result.setTotal(Long.parseLong(String.valueOf(hits.get("total"))));
        JSONArray hitsArray = (JSONArray)hits.get("hits");
        List<AlbumMeta> arrAlbumMeta = new ArrayList<>();

        int i = 0;
        int size = hitsArray.length();

        for (; i < size; i++) {
            AlbumMeta meta = new AlbumMeta();
            JSONObject source = (JSONObject)hitsArray.getJSONObject(i).get("_source");
            if(hitsArray.getJSONObject(i).has("_id")) meta.setId(checkLongNull(hitsArray.getJSONObject(i).get("_id")));
            if(source.has("originid")) meta.setOriginid(checkLongNull(source.get("originid")));
            if(source.has("name")) meta.setName(String.valueOf(source.get("name")));
            if(source.has("name_en")) meta.setNameEn(String.valueOf(source.get("name_en")));
            if(source.has("artistname")) meta.setArtistName(String.valueOf(source.get("artistname")));
            if(source.has("artistname_en")) meta.setArtistNameEn(String.valueOf(source.get("artistname_en")));

            arrAlbumMeta.add(meta);
        }

        result.setData(arrAlbumMeta);

        return result;
    }

    /**
     * Kibana 의 Console Search 로는 다음과 같이 표현 할 수 있다.
     GET /meta/song/_search
     {
        "query": {
            "match_phrase_prefix": {
                "name" : prefix
            }
        }
     }
     * @param prefix
     * @return
     */
    public EsResult findSongMetaByPrefix(String prefix) {
        EsResult result = new EsResult();

        JSONObject hits = elasticsearchUtil.searchItem(
                0,
                "song",
                prefix
        );

        result.setTotal(Long.parseLong(String.valueOf(hits.get("total"))));
        JSONArray hitsArray = (JSONArray)hits.get("hits");
        List<SongMeta> arrSongMeta = new ArrayList<>();

        int i = 0;
        int size = hitsArray.length();

        for (; i < size; i++) {
            SongMeta meta = new SongMeta();
            JSONObject source = (JSONObject)hitsArray.getJSONObject(i).get("_source");
            if(hitsArray.getJSONObject(i).has("_id")) meta.setId(checkLongNull(hitsArray.getJSONObject(i).get("_id")));
            if(source.has("originid")) meta.setOriginid(checkLongNull(source.get("originid")));
            if(source.has("name")) meta.setName(String.valueOf(source.get("name")));
            if(source.has("name_en")) meta.setNameEn(String.valueOf(source.get("name_en")));
            if(source.has("artistname")) meta.setArtistName(String.valueOf(source.get("artistname")));
            if(source.has("artistname_en")) meta.setArtistNameEn(String.valueOf(source.get("artistname_en")));

            arrSongMeta.add(meta);
        }

        result.setData(arrSongMeta);

        return result;
    }

    /**
     * Kibana 의 Console Search 로는 다음과 같이 표현 할 수 있다.
     GET /meta/artist/_search
     {
        "from" : 0, "size" : 20,        // 응용의 방법 0 부터 20개 가져오기
        "query": {
            "match_phrase_prefix": {
                "name" : prefix
            }
        }
     }
     * @param prefix
     * @return
     */
    public EsResult findArtistMetaByPrefix(String prefix) {
        EsResult result = new EsResult();

        JSONObject hits = elasticsearchUtil.searchItem(
                0,
                "artist",
                EsSearchType.MATCH_PHRASE_PREFIX,
                prefix,
                "name",
                EsOrder.ASC
        );

        result.setTotal(Long.parseLong(String.valueOf(hits.get("total"))));
        JSONArray hitsArray = (JSONArray)hits.get("hits");
        List<ArtistMeta> arrArtistMeta = new ArrayList<>();

        int i = 0;
        int size = hitsArray.length();

        for (; i < size; i++) {
            ArtistMeta meta = new ArtistMeta();
            JSONObject source = (JSONObject)hitsArray.getJSONObject(i).get("_source");
            if(hitsArray.getJSONObject(i).has("_id")) meta.setId(checkLongNull(hitsArray.getJSONObject(i).get("_id")));
            if(source.has("originid")) meta.setOriginid(checkLongNull(source.get("originid")));
            if(source.has("name"))meta.setName(String.valueOf(source.get("name")));
            if(source.has("name_en")) meta.setNameEn(String.valueOf(source.get("name_en")));
            arrArtistMeta.add(meta);
        }

        result.setData(arrArtistMeta);

        return result;
    }

    /**
     * ElasticSearch 메타 데이터 입력
     * @param metaType   album, artist, song ...
     * @param id         id
     * @param originId   originid
     * @param name       앨범 이름 등 ...
     * @param artistNameEn  앨범 이름 등 ... 영문
     * @param artistName 해당 맵핑된 아티스트 이름 ... (metaInfo 일 경우는 null)
     * @param artistNameEn 해당 맵핑된 아티스트 이름 ... (metaInfo 일 경우는 null) 영문
     * @return
     */
    public boolean putMetaData(
            EsMetaType metaType,
            Long id,
            Long originId,
            String name,
            String nameEn ,
            String artistName,
            String artistNameEn
    )
    {
        // null check 구문 추가
        if(name == null) name = "";
        if(nameEn == null) nameEn = "";
        if(artistName == null) artistName = "";
        if(artistNameEn == null) artistNameEn = "";

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (originId != null) {
           sb.append("\"originid\":");
           sb.append(originId).append(",");
        }
        sb.append("\"name\":\"");
        sb.append(name);
        sb.append("\",\"name_en\":\"");
        sb.append(nameEn);
        sb.append("\",\"keyword\":\"");
        sb.append(getKeyword(metaType, artistName, name).toLowerCase());
        sb.append("\",\"keyword_en\":\"");
        sb.append(getKeyword(metaType, artistNameEn, nameEn).toLowerCase());
        sb.append("\"");
        if (!metaType.equals(EsMetaType.ARTIST)) {
            sb.append(",\"artistname\":\"");
            sb.append(artistName);
            sb.append("\"");
            sb.append(",\"artistname_en\":\"");
            sb.append(artistNameEn);
            sb.append("\"");
        }
        sb.append("}");


        boolean result = elasticsearchUtil.putItem(
               metaType.getMetaType() + "/doc",
               id,
               sb.toString()
        );

        if(!result) logger.error("[putMeta Error] " + sb.toString());

        return  result;
    }

    // 영문 데이터가 없을 때
    public boolean putMetaData(
        EsMetaType metaType,
        Long id,
        Long originId,
        String name,
        String artistName
    )
    {
        return this.putMetaData(metaType, id, originId, name, null, artistName, null);
    }

    public void deleteMetaData(
            EsMetaType metaType,
            Long id
    ) {
        elasticsearchUtil.deleteItem(
//                "/meta/"+metaType.getMetaType(),
                metaType.getMetaType() + "/doc",
                id
        );
    }

    private Long checkLongNull(Object check) {
        if (String.valueOf(check).equalsIgnoreCase("null")) {
            return null;
        }
        try {
            return Long.parseLong(String.valueOf(check));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getKeyword(EsMetaType esMetaType, String artistName, String name) {
        if (esMetaType.equals(EsMetaType.SONG)) {
            /**
             * 1. 곡이름만
             * 2. 각 가수별 이름 + 곡이름
             * 3. 각 object 간은 띄어쓰기 구분
             * 4. 하나의 object 는 붙여쓰기
             * 5. 두명 까지는 교차로 검색어 입력
             * 6. 세명 부터는 아티스트 이름 + 곡 이름
             * 7. 괄호 이후는 전부 삭제 처리
             */
            name = name.replaceAll(" ", "").trim();
            if (name.contains("(")) {
                name = name.substring(0, name.indexOf("(")).trim();
            }
            artistName = artistName.replaceAll(" ", "").trim();
            if (artistName.contains(",")) {
                String[] artists = artistName.split(",");
                if (artists.length == 2) {
                    if (artistName.contains(".")) {

                    } else {
                        return name + " " +
                                artists[0]+name + " " +
                                artists[1]+name + " " +
                                artists[0]+artists[1]+name + " " +
                                artists[1]+artists[0]+name;
                    }
                } else {
                    return name + " " + artistName+name;
                }
            }
            return name + " " + artistName+name;
        } else if (esMetaType.equals(EsMetaType.ARTIST)) {
            if (name == null || name.equalsIgnoreCase("null") || name.indexOf("null") == 0) {
                return "";
            }

            String keyword = name.replaceAll(" ", "").trim();
            if (keyword.contains(".")) {
                keyword = keyword + " " + name.replaceAll("\\.","");
            }
            return keyword;
        } else {
            return name.replaceAll(" ", "").trim();
        }
    }

}

package kr.mubeat.cms.domain.es;

/**
 * Created by doohwan.yoo on 2017. 5. 22..
 */
public class SongMeta {

    private Long id; /** 곡 아이디 입니다 **/
    private Long originid;
    private String name;
    private String nameEn;
    private String artistName;
    private String artistNameEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() { return nameEn; }

    public void setNameEn(String nameEn) { this.nameEn = nameEn; }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getArtistNameEn() {
        return artistNameEn;
    }

    public void setArtistNameEn(String artistNameEn) { this.artistNameEn = artistNameEn; }

    public Long getOriginid() {
        return originid;
    }

    public void setOriginid(Long originid) {
        this.originid = originid;
    }
}

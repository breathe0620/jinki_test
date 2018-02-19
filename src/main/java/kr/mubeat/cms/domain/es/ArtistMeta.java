package kr.mubeat.cms.domain.es;

/**
 * Created by doohwan.yoo on 2017. 5. 22..
 */
public class ArtistMeta {

    private Long id;
    private String name;
    private String nameEn;
    private Long originid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {  this.nameEn = nameEn; }

    public Long getOriginid() {
        return originid;
    }

    public void setOriginid(Long originid) { this.originid = originid; }
}

package kr.mubeat.cms.domain.meta.song;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 */
@Entity
@Table(name = "tbl_song_genre")
public class SongGenre implements Serializable {

    private static final long serialVersionUID = -2502980427475251350L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="genreid", nullable = false, insertable = false, updatable = false)
    private Integer genreId;

    @Column(name="genrename", nullable = false)
    private String genreName;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}

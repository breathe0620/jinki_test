package kr.mubeat.cms.dto.meta.acquisition;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.mubeat.cms.domain.meta.clip.ClipMedia;
import kr.mubeat.cms.dto.meta.clip.ClipMediaDTO;
import kr.mubeat.cms.dto.meta.clip.ContentDTO;
import kr.mubeat.cms.dto.meta.clip.ProgramDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 5. 10..
 * 방송입수정보 상세 DTO ToDo 추 후 미디어 파일 URL 경로도 넘겨주도록 변경 할지 검토
 */
@JsonSerialize
public class AcquisitionDetailDTO implements Serializable {

    private static final long serialVersionUID = 6136617001084233823L;

    private ContentDTO content;
    private ProgramDTO program;
    private AcquisitionDTO clip;
    private List<ClipMedia> clipMediaList;
    private String originalPath;

    public AcquisitionDetailDTO() {}

    public ContentDTO getContent() {
        return content;
    }

    public void setContent(ContentDTO content) {
        this.content = content;
    }

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }

    public AcquisitionDTO getClip() {
        return clip;
    }

    public void setClip(AcquisitionDTO clip) {
        this.clip = clip;
    }

    public List<ClipMedia> getClipMediaList() {
        return clipMediaList;
    }

    public void setClipMediaList(List<ClipMedia> clipMediaList) {
        this.clipMediaList = clipMediaList;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }
}

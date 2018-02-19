package kr.mubeat.cms.repository.meta.clip;

import kr.mubeat.cms.domain.meta.clip.ClipMedia;
import kr.mubeat.cms.domain.meta.clip.ClipMeta;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.clip.*;
import kr.mubeat.cms.dto.meta.program.ProgramMetaDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 20..
 */
public interface ClipRepositoryCustom {

    Page<ClipDTO> getClipList(CustomSearchParam searchParam);

    List<ClipExcelDTO> getClipsForExcel(CustomSearchParam searchParam);

    ClipDTO getClipDetail(Long clipMetaId);

    List<ClassifyDTO> getClassify();

    Long updateClipInfo(Long clipMetaId, ClipMeta clipMeta);

    TranscodeDTO getTranscodeStates();

    List<ClipEsDTO> getAllClipForElasticSearch(int page);
    List<Long> getAllDeletedClipForElasticSearch(int page);

    List<ClipEsDTO> getClipForElasticSearch(Long clipMetaId);

    List<ClipMedia> getClipMedia(Long clipMetaId);

}

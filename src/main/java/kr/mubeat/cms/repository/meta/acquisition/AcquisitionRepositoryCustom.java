package kr.mubeat.cms.repository.meta.acquisition;

import kr.mubeat.cms.domain.meta.clip.ClipMedia;
import kr.mubeat.cms.domain.meta.clip.Transcode;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.acquisition.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 5..
 */
public interface AcquisitionRepositoryCustom {

    Page<AcquisitionListDTO> getAcquisitionList(CustomSearchParam searchParam);
    Page<AcquisitionListDTO> getAcquisitionStateList(CustomSearchParam searchParam);

    Long updateOriginS3Location(Long id, String originS3Location);

    Long updateAcquisitionInformation(Long id, String clipTitle);

    List<Transcode> getUploadState(List<Long> id);
    Long updateTranscode(List<Long> id, String type);

    /**
     * 풀 셋
     */
    ClipDTO getClip(Long clipMetaId);
    ClipMetaDTO getClipMeta(Long clipMetaId);
    ClipAcquisitionDTO getClipAcquisition(Long clipMetaId);
    ContentDTO getContent(Long clipMetaId);
    List<ClipMedia> getClipMediaList(Long clipMetaId);
    String getOriginalClipPath(Long clipMetaId);

}

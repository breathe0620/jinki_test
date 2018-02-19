package kr.mubeat.cms.service.meta.clip;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.dto.meta.clip.ClassifyDTO;
import kr.mubeat.cms.dto.meta.clip.ClipDTO;
import kr.mubeat.cms.dto.meta.clip.TranscodeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 20..
 */
public interface ClipService {

    CustomPageResult getClipList(CustomSearchParam searchParam);
    byte[] getClipsForExcel(CustomSearchParam searchParam);

//    HashMap<Integer, String> xlsxToUpdateClipMeta(MultipartFile file);
    Result xlsxToUpdateClipMeta(MultipartFile file);

    ClipDTO getClipDetail(Long metaId);

    Result updateClipInfo(Long clipMetaId, ClipDTO param);

    List<ClassifyDTO> getClassify();

    TranscodeDTO getTranscodeStates();

    Result getAndPutAllClipForElasticSearch();

}

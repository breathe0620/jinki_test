package kr.mubeat.cms.service.meta.acquisition;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ClipAcquisitionDetailDTO;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
public interface AcquisitionService {

    CustomPageResult getAcquisitionList(CustomSearchParam searchParam);
    CustomPageResult getAcquisitionStateList(CustomSearchParam searchParam);

    ClipAcquisitionDetailDTO getAcquisitionDetail(Long metaId);

    Result updateUploadState(List<Long> id);

    Result updateAcquisitionInformation(Long id, String clipTitle);

}

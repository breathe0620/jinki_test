package kr.mubeat.cms.service.meta.agency;

import kr.mubeat.cms.domain.meta.agency.Agency;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.agency.AgencyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
public interface AgencyService {

    List<Agency> getAllAgency();
    Result getAgency(Pageable pageable);
    Result findAllByAgencyName(String agencyName);
    Result addAgency(Agency param);
    Result updateAgency(Integer agencyId, AgencyDTO param);
    Result deleteAgency(Integer agencyId);
    Result getSameAgencyArtist(Integer agencyId);

    byte[] getAgencyForExcel();
    Result xlsxToUpdateAgencyMeta(MultipartFile file);

}

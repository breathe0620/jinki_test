package kr.mubeat.cms.repository.meta.program;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.dto.meta.program.ProgramMetaDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 21..
 */
public interface ProgramMetaRepositoryCustom {

    Page<ProgramMetaDTO> getProgramMetaList(CustomSearchParam searchParam);

    List<String> getCorporators();
    List<ProgramSearchDTO> getPrograms(String corporator);

    List<ProgramMetaDTO> getProgramForExcel();

    Long updateProgramTitleEn(String programId, String programTitleEn);

}

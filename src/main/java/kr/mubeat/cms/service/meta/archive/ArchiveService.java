package kr.mubeat.cms.service.meta.archive;

import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.dto.meta.archive.ArchiveMBC;
import kr.mubeat.cms.dto.meta.archive.ArchiveMeta;
import kr.mubeat.cms.dto.meta.archive.ArchiveS3DTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 19..
 */
public interface ArchiveService {

    List<ArchiveS3DTO> getList(String path);
    ArchiveMBC parseXML(String xml, String video);
    Result clipRegist(ArchiveMeta archiveMeta, MultipartFile video, MultipartFile thumbImg);

    List<String> getCorporators();
    List<ProgramSearchDTO> getPrograms(String corporator);

}

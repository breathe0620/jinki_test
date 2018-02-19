package kr.mubeat.cms.service.recommend.weekprogram.impl;

import kr.mubeat.cms.domain.recommend.weekprogram.WeekProgramItem;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.program.WeekProgramItemDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.recommend.weekprogram.WeekProgramItemRepository;
import kr.mubeat.cms.service.recommend.weekprogram.WeekProgramItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 9..
 */
@Service
public class WeekProgramItemServiceImpl implements WeekProgramItemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private WeekProgramItemRepository weekProgramItemRepository;

    @Autowired
    public WeekProgramItemServiceImpl(
        WeekProgramItemRepository weekProgramItemRepository
    ) {
        this.weekProgramItemRepository = weekProgramItemRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Result getProgramBroadDates(String programId) {
        Result result = new Result(EnumResCode.OK);
        result.setData(weekProgramItemRepository.getProgramBroadDates(programId));
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Result getProgramsByBroadDate(
            String programId,
            String broadDate,
            Integer recommendProgramId
    ) {
        Result result = new Result(EnumResCode.OK);
        result.setData(weekProgramItemRepository.getProgramsByBroadDate(
                programId, broadDate, recommendProgramId
        ));
        return result;
    }

    @Override
    public Result getProgramitems(Integer recommendProgramId) {
        Result result = new Result(EnumResCode.OK);
        result.setData(weekProgramItemRepository.getProgramItems(recommendProgramId));
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result addClip(List<WeekProgramItemDTO> weekProgramItems) {

         List<WeekProgramItem> weekProgramItemList = new ArrayList<>();

        for (WeekProgramItemDTO weekProgramItem : weekProgramItems) {
            weekProgramItemList.add(
                new WeekProgramItem(
                    weekProgramItem.getRecommendProgramId(),
                    weekProgramItem.getClipMetaId()
                )
            );
        }
        weekProgramItemRepository.save(weekProgramItemList);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteClip(List<WeekProgramItemDTO> weekProgramItems) {

        List<WeekProgramItem> weekProgramItemList = new ArrayList<>();

         for (WeekProgramItemDTO weekProgramItem : weekProgramItems) {
            weekProgramItemList.add(
                new WeekProgramItem(
                    weekProgramItem.getRecommendProgramId(),
                    weekProgramItem.getClipMetaId()
                )
            );
        }
        weekProgramItemRepository.deleteInBatch(weekProgramItemList);

        return new Result(EnumResCode.OK);
    }
}

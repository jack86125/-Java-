package com.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.system.entity.ScoreHistory;

import java.util.List;

/**
 * 成绩修改历史Service接口
 */
public interface ScoreHistoryService extends IService<ScoreHistory> {

    /**
     * 根据成绩ID查询修改历史
     */
    List<ScoreHistory> getHistoryByScoreId(Long scoreId);

}

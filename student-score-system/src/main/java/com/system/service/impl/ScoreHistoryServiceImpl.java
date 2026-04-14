package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.entity.ScoreHistory;
import com.system.mapper.ScoreHistoryMapper;
import com.system.service.ScoreHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 成绩修改历史Service实现类
 */
@Service
public class ScoreHistoryServiceImpl extends ServiceImpl<ScoreHistoryMapper, ScoreHistory> implements ScoreHistoryService {

    @Override
    public List<ScoreHistory> getHistoryByScoreId(Long scoreId) {
        LambdaQueryWrapper<ScoreHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ScoreHistory::getScoreId, scoreId);
        wrapper.orderByDesc(ScoreHistory::getCreateTime);
        return this.list(wrapper);
    }

}

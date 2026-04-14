package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.entity.Score;
import com.system.entity.ScoreHistory;
import com.system.exception.BusinessException;
import com.system.mapper.ScoreMapper;
import com.system.service.ScoreHistoryService;
import com.system.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 成绩Service实现类
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Autowired
    private ScoreHistoryService scoreHistoryService;

    @Override
    public Page<Score> getScorePage(Long current, Long size, Long studentId, Long courseId, String semester) {
        Page<Score> page = new Page<>(current, size);
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();

        if (studentId != null) {
            wrapper.eq(Score::getStudentId, studentId);
        }
        if (courseId != null) {
            wrapper.eq(Score::getCourseId, courseId);
        }
        if (StringUtils.hasText(semester)) {
            wrapper.eq(Score::getSemester, semester);
        }

        wrapper.orderByDesc(Score::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addScore(Score score) {
        // 检查是否已存在相同的成绩记录
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Score::getStudentId, score.getStudentId())
                .eq(Score::getCourseId, score.getCourseId())
                .eq(Score::getExamType, score.getExamType())
                .eq(Score::getSemester, score.getSemester());

        if (this.count(wrapper) > 0) {
            throw new BusinessException("该学生在此学期的该课程该考试类型的成绩已存在");
        }

        return this.save(score);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateScore(Score score, Long operatorId, String operatorName, String reason) {
        // 获取原成绩
        Score oldScore = this.getById(score.getId());
        if (oldScore == null) {
            throw new BusinessException("成绩不存在");
        }

        // 记录成绩修改历史
        ScoreHistory history = new ScoreHistory();
        history.setScoreId(score.getId());
        history.setOldScore(oldScore.getScore());
        history.setNewScore(score.getScore());
        history.setOperatorId(operatorId);
        history.setOperatorName(operatorName);
        history.setReason(reason);
        scoreHistoryService.save(history);

        // 更新成绩
        return this.updateById(score);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteScore(Long id) {
        return this.removeById(id);
    }

    @Override
    public List<Score> getScoresByStudentId(Long studentId) {
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Score::getStudentId, studentId);
        wrapper.orderByDesc(Score::getCreateTime);
        return this.list(wrapper);
    }

    @Override
    public List<Score> getScoresByCourseId(Long courseId) {
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Score::getCourseId, courseId);
        wrapper.orderByDesc(Score::getCreateTime);
        return this.list(wrapper);
    }

}

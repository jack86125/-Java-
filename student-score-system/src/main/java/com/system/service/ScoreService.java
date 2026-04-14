package com.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.system.entity.Score;

import java.util.List;

/**
 * 成绩Service接口
 */
public interface ScoreService extends IService<Score> {

    /**
     * 分页查询成绩列表
     */
    Page<Score> getScorePage(Long current, Long size, Long studentId, Long courseId, String semester);

    /**
     * 添加成绩
     */
    boolean addScore(Score score);

    /**
     * 更新成绩
     */
    boolean updateScore(Score score, Long operatorId, String operatorName, String reason);

    /**
     * 删除成绩
     */
    boolean deleteScore(Long id);

    /**
     * 根据学生ID查询成绩列表
     */
    List<Score> getScoresByStudentId(Long studentId);

    /**
     * 根据课程ID查询成绩列表
     */
    List<Score> getScoresByCourseId(Long courseId);

}

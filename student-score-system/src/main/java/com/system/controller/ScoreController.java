package com.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Score;
import com.system.entity.ScoreHistory;
import com.system.service.ScoreHistoryService;
import com.system.service.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成绩Controller
 */
@Tag(name = "成绩管理接口")
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScoreHistoryService scoreHistoryService;

    /**
     * 分页查询成绩列表
     */
    @Operation(summary = "分页查询成绩列表")
    @GetMapping("/list")
    public Result<Page<Score>> getScoreList(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String semester) {
        Page<Score> page = scoreService.getScorePage(current, size, studentId, courseId, semester);
        return Result.success(page);
    }

    /**
     * 根据ID查询成绩详情
     */
    @Operation(summary = "查询成绩详情")
    @GetMapping("/{id}")
    public Result<Score> getScoreById(@PathVariable Long id) {
        Score score = scoreService.getById(id);
        return Result.success(score);
    }

    /**
     * 添加成绩
     */
    @Operation(summary = "添加成绩")
    @PostMapping
    public Result<String> addScore(@RequestBody Score score) {
        scoreService.addScore(score);
        return Result.success("添加成功");
    }

    /**
     * 更新成绩
     */
    @Operation(summary = "更新成绩")
    @PutMapping
    public Result<String> updateScore(
            @RequestBody Score score,
            @RequestParam Long operatorId,
            @RequestParam String operatorName,
            @RequestParam(required = false) String reason) {
        scoreService.updateScore(score, operatorId, operatorName, reason);
        return Result.success("更新成功");
    }

    /**
     * 删除成绩
     */
    @Operation(summary = "删除成绩")
    @DeleteMapping("/{id}")
    public Result<String> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        return Result.success("删除成功");
    }

    /**
     * 根据学生ID查询成绩列表
     */
    @Operation(summary = "根据学生ID查询成绩")
    @GetMapping("/student/{studentId}")
    public Result<List<Score>> getScoresByStudentId(@PathVariable Long studentId) {
        List<Score> scores = scoreService.getScoresByStudentId(studentId);
        return Result.success(scores);
    }

    /**
     * 根据课程ID查询成绩列表
     */
    @Operation(summary = "根据课程ID查询成绩")
    @GetMapping("/course/{courseId}")
    public Result<List<Score>> getScoresByCourseId(@PathVariable Long courseId) {
        List<Score> scores = scoreService.getScoresByCourseId(courseId);
        return Result.success(scores);
    }

    /**
     * 查询成绩修改历史
     */
    @Operation(summary = "查询成绩修改历史")
    @GetMapping("/history/{scoreId}")
    public Result<List<ScoreHistory>> getScoreHistory(@PathVariable Long scoreId) {
        List<ScoreHistory> history = scoreHistoryService.getHistoryByScoreId(scoreId);
        return Result.success(history);
    }

}

package com.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩修改历史实体类
 */
@Data
@TableName("score_history")
public class ScoreHistory {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 成绩ID
     */
    private Long scoreId;

    /**
     * 原成绩
     */
    private BigDecimal oldScore;

    /**
     * 新成绩
     */
    private BigDecimal newScore;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 修改原因
     */
    private String reason;

    /**
     * 修改时间
     */
    private LocalDateTime createTime;

}

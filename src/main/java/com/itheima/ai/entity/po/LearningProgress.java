package com.itheima.ai.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("learning_progress")
public class LearningProgress {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String userId;
    
    private String courseId;
    
    private String courseName;
    
    private String chapterName;
    
    private Integer totalChapters;
    
    private Integer completedChapters;
    
    private Integer studyTimeMinutes;
    
    private Integer score;
    
    private String status;
    
    private LocalDateTime lastStudyTime;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
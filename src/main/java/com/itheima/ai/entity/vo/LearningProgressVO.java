package com.itheima.ai.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LearningProgressVO {
    
    private String userId;
    
    private String courseId;
    
    private String courseName;
    
    private String chapterName;
    
    private Integer totalChapters;
    
    private Integer completedChapters;
    
    private Double completionRate;
    
    private Integer studyTimeMinutes;
    
    private Integer score;
    
    private String status;
    
    private LocalDateTime lastStudyTime;
    
    private String studyStreak;
    
    private String nextSuggestion;
}
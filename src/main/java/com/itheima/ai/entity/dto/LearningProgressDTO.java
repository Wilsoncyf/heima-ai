package com.itheima.ai.entity.dto;

import lombok.Data;

@Data
public class LearningProgressDTO {
    
    private String userId;
    
    private String courseId;
    
    private String courseName;
    
    private String chapterName;
    
    private Integer studyTimeMinutes;
    
    private Integer score;
    
    private String status;
}
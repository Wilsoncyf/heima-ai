package com.itheima.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.ai.entity.dto.LearningProgressDTO;
import com.itheima.ai.entity.po.LearningProgress;
import com.itheima.ai.entity.vo.LearningProgressVO;

import java.util.List;

public interface ILearningProgressService extends IService<LearningProgress> {
    
    void updateProgress(LearningProgressDTO dto);
    
    LearningProgressVO getProgressByUser(String userId);
    
    List<LearningProgressVO> getProgressByCourse(String courseId);
    
    String generateLearningReport(String userId);
    
    List<String> getStudySuggestions(String userId);
}
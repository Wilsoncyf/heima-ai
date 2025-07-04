package com.itheima.ai.mcp;

import com.itheima.ai.entity.dto.LearningProgressDTO;
import com.itheima.ai.entity.vo.LearningProgressVO;
import com.itheima.ai.service.ILearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.mcp.server.tool.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LearningProgressMcpTool {
    
    private final ILearningProgressService learningProgressService;
    
    @McpTool(name = "updateLearningProgress", description = "更新学习进度")
    public String updateLearningProgress(
            String userId, 
            String courseId, 
            String courseName, 
            String chapterName, 
            int studyTimeMinutes, 
            int score, 
            String status) {
        
        LearningProgressDTO dto = new LearningProgressDTO();
        dto.setUserId(userId);
        dto.setCourseId(courseId);
        dto.setCourseName(courseName);
        dto.setChapterName(chapterName);
        dto.setStudyTimeMinutes(studyTimeMinutes);
        dto.setScore(score);
        dto.setStatus(status);
        
        learningProgressService.updateProgress(dto);
        
        return "学习进度更新成功！用户：" + userId + "，课程：" + courseName + "，章节：" + chapterName;
    }
    
    @McpTool(name = "getLearningProgress", description = "获取用户学习进度")
    public LearningProgressVO getLearningProgress(String userId) {
        return learningProgressService.getProgressByUser(userId);
    }
    
    @McpTool(name = "generateLearningReport", description = "生成学习报告")
    public String generateLearningReport(String userId) {
        return learningProgressService.generateLearningReport(userId);
    }
    
    @McpTool(name = "getStudySuggestions", description = "获取学习建议")
    public List<String> getStudySuggestions(String userId) {
        return learningProgressService.getStudySuggestions(userId);
    }
    
    @McpTool(name = "getCourseProgress", description = "获取课程整体进度")
    public List<LearningProgressVO> getCourseProgress(String courseId) {
        return learningProgressService.getProgressByCourse(courseId);
    }
    
    @McpTool(name = "checkLearningStreak", description = "检查学习连续性")
    public String checkLearningStreak(String userId) {
        LearningProgressVO progress = learningProgressService.getProgressByUser(userId);
        if (progress == null) {
            return "用户还没有学习记录";
        }
        
        return "用户 " + userId + " 的学习连续性：" + progress.getStudyStreak() + 
               "。建议：" + progress.getNextSuggestion();
    }
    
    @McpTool(name = "calculateStudyEfficiency", description = "计算学习效率")
    public String calculateStudyEfficiency(String userId) {
        LearningProgressVO progress = learningProgressService.getProgressByUser(userId);
        if (progress == null) {
            return "用户还没有学习记录";
        }
        
        double efficiency = progress.getCompletedChapters() > 0 ? 
            (double) progress.getScore() / progress.getStudyTimeMinutes() * 100 : 0;
        
        return String.format("用户 %s 的学习效率：%.2f分/小时。当前完成度：%.1f%%", 
                userId, efficiency, progress.getCompletionRate());
    }
}
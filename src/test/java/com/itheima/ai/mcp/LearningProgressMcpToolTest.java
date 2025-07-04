package com.itheima.ai.mcp;

import com.itheima.ai.entity.vo.LearningProgressVO;
import com.itheima.ai.service.ILearningProgressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class LearningProgressMcpToolTest {
    
    @Autowired
    private LearningProgressMcpTool learningProgressMcpTool;
    
    @Autowired
    private ILearningProgressService learningProgressService;
    
    @Test
    void testUpdateLearningProgress() {
        String result = learningProgressMcpTool.updateLearningProgress(
                "test_user", 
                "java_basic", 
                "Java基础课程", 
                "第1章：Java入门", 
                30, 
                85, 
                "in_progress"
        );
        
        assertNotNull(result);
        assertTrue(result.contains("学习进度更新成功"));
    }
    
    @Test
    void testGetLearningProgress() {
        // 先更新一个进度
        learningProgressMcpTool.updateLearningProgress(
                "test_user2", 
                "spring_boot", 
                "Spring Boot实战", 
                "第2章：自动配置", 
                45, 
                92, 
                "in_progress"
        );
        
        // 然后查询进度
        LearningProgressVO progress = learningProgressMcpTool.getLearningProgress("test_user2");
        
        assertNotNull(progress);
        assertEquals("test_user2", progress.getUserId());
        assertEquals("spring_boot", progress.getCourseId());
        assertEquals("Spring Boot实战", progress.getCourseName());
    }
    
    @Test
    void testGenerateLearningReport() {
        // 先更新一个进度
        learningProgressMcpTool.updateLearningProgress(
                "test_user3", 
                "mysql_advanced", 
                "MySQL高级课程", 
                "第3章：索引优化", 
                60, 
                78, 
                "completed"
        );
        
        String report = learningProgressMcpTool.generateLearningReport("test_user3");
        
        assertNotNull(report);
        assertTrue(report.contains("学习进度报告"));
        assertTrue(report.contains("MySQL高级课程"));
    }
    
    @Test
    void testGetStudySuggestions() {
        // 先更新一个进度
        learningProgressMcpTool.updateLearningProgress(
                "test_user4", 
                "react_basic", 
                "React基础课程", 
                "第1章：React入门", 
                30, 
                80, 
                "in_progress"
        );
        
        List<String> suggestions = learningProgressMcpTool.getStudySuggestions("test_user4");
        
        assertNotNull(suggestions);
        assertFalse(suggestions.isEmpty());
        assertTrue(suggestions.size() > 0);
    }
    
    @Test
    void testCheckLearningStreak() {
        // 先更新一个进度
        learningProgressMcpTool.updateLearningProgress(
                "test_user5", 
                "vue_basic", 
                "Vue基础课程", 
                "第1章：Vue入门", 
                25, 
                88, 
                "in_progress"
        );
        
        String streak = learningProgressMcpTool.checkLearningStreak("test_user5");
        
        assertNotNull(streak);
        assertTrue(streak.contains("学习连续性"));
    }
    
    @Test
    void testCalculateStudyEfficiency() {
        // 先更新一个进度
        learningProgressMcpTool.updateLearningProgress(
                "test_user6", 
                "python_basic", 
                "Python基础课程", 
                "第2章：Python语法", 
                40, 
                90, 
                "in_progress"
        );
        
        String efficiency = learningProgressMcpTool.calculateStudyEfficiency("test_user6");
        
        assertNotNull(efficiency);
        assertTrue(efficiency.contains("学习效率"));
    }
}
package com.itheima.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.ai.entity.dto.LearningProgressDTO;
import com.itheima.ai.entity.po.LearningProgress;
import com.itheima.ai.entity.vo.LearningProgressVO;
import com.itheima.ai.mapper.LearningProgressMapper;
import com.itheima.ai.service.ILearningProgressService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearningProgressServiceImpl extends ServiceImpl<LearningProgressMapper, LearningProgress> 
        implements ILearningProgressService {

    @Override
    public void updateProgress(LearningProgressDTO dto) {
        QueryWrapper<LearningProgress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", dto.getUserId())
               .eq("course_id", dto.getCourseId());
        
        LearningProgress progress = getOne(wrapper);
        if (progress == null) {
            progress = new LearningProgress();
            progress.setUserId(dto.getUserId());
            progress.setCourseId(dto.getCourseId());
            progress.setCourseName(dto.getCourseName());
            progress.setTotalChapters(10);
            progress.setCompletedChapters(0);
            progress.setStudyTimeMinutes(0);
            progress.setScore(0);
            progress.setCreateTime(LocalDateTime.now());
        }
        
        progress.setChapterName(dto.getChapterName());
        progress.setStudyTimeMinutes(progress.getStudyTimeMinutes() + dto.getStudyTimeMinutes());
        progress.setScore(dto.getScore());
        progress.setStatus(dto.getStatus());
        progress.setLastStudyTime(LocalDateTime.now());
        progress.setUpdateTime(LocalDateTime.now());
        
        if ("completed".equals(dto.getStatus())) {
            progress.setCompletedChapters(progress.getCompletedChapters() + 1);
        }
        
        saveOrUpdate(progress);
    }

    @Override
    public LearningProgressVO getProgressByUser(String userId) {
        QueryWrapper<LearningProgress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        
        LearningProgress progress = getOne(wrapper);
        if (progress == null) {
            return null;
        }
        
        return convertToVO(progress);
    }

    @Override
    public List<LearningProgressVO> getProgressByCourse(String courseId) {
        QueryWrapper<LearningProgress> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        
        List<LearningProgress> progressList = list(wrapper);
        return progressList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public String generateLearningReport(String userId) {
        LearningProgressVO progress = getProgressByUser(userId);
        if (progress == null) {
            return "用户还没有学习记录";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("📚 学习进度报告\n");
        report.append("课程：").append(progress.getCourseName()).append("\n");
        report.append("进度：").append(progress.getCompletedChapters())
              .append("/").append(progress.getTotalChapters())
              .append(" (").append(String.format("%.1f", progress.getCompletionRate())).append("%)\n");
        report.append("学习时长：").append(progress.getStudyTimeMinutes()).append("分钟\n");
        report.append("当前得分：").append(progress.getScore()).append("分\n");
        report.append("学习状态：").append(progress.getStatus()).append("\n");
        report.append("最后学习：").append(progress.getLastStudyTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
        report.append("连续学习：").append(progress.getStudyStreak()).append("\n");
        
        return report.toString();
    }

    @Override
    public List<String> getStudySuggestions(String userId) {
        LearningProgressVO progress = getProgressByUser(userId);
        if (progress == null) {
            return List.of("开始你的学习之旅吧！");
        }
        
        return List.of(
            "建议每天学习30分钟，保持学习习惯",
            "可以复习之前学过的章节：" + progress.getChapterName(),
            "你已经完成了 " + progress.getCompletionRate() + "% 的课程，继续加油！",
            "距离完成课程还需要大约 " + calculateRemainingTime(progress) + " 小时"
        );
    }
    
    private LearningProgressVO convertToVO(LearningProgress progress) {
        LearningProgressVO vo = new LearningProgressVO();
        vo.setUserId(progress.getUserId());
        vo.setCourseId(progress.getCourseId());
        vo.setCourseName(progress.getCourseName());
        vo.setChapterName(progress.getChapterName());
        vo.setTotalChapters(progress.getTotalChapters());
        vo.setCompletedChapters(progress.getCompletedChapters());
        vo.setCompletionRate(progress.getTotalChapters() > 0 ? 
            (double) progress.getCompletedChapters() / progress.getTotalChapters() * 100 : 0);
        vo.setStudyTimeMinutes(progress.getStudyTimeMinutes());
        vo.setScore(progress.getScore());
        vo.setStatus(progress.getStatus());
        vo.setLastStudyTime(progress.getLastStudyTime());
        vo.setStudyStreak(calculateStudyStreak(progress));
        vo.setNextSuggestion(generateNextSuggestion(progress));
        
        return vo;
    }
    
    private String calculateStudyStreak(LearningProgress progress) {
        if (progress.getLastStudyTime() == null) {
            return "0天";
        }
        
        long daysBetween = ChronoUnit.DAYS.between(progress.getLastStudyTime(), LocalDateTime.now());
        if (daysBetween == 0) {
            return "今天已学习";
        } else if (daysBetween == 1) {
            return "昨天学习";
        } else {
            return daysBetween + "天前学习";
        }
    }
    
    private String generateNextSuggestion(LearningProgress progress) {
        if (progress.getCompletedChapters() >= progress.getTotalChapters()) {
            return "恭喜完成课程！可以开始新的学习挑战";
        }
        
        double completionRate = (double) progress.getCompletedChapters() / progress.getTotalChapters();
        if (completionRate < 0.3) {
            return "建议先完成基础章节，打好基础";
        } else if (completionRate < 0.7) {
            return "进度不错，继续保持学习节奏";
        } else {
            return "即将完成课程，最后冲刺！";
        }
    }
    
    private int calculateRemainingTime(LearningProgressVO progress) {
        int remainingChapters = progress.getTotalChapters() - progress.getCompletedChapters();
        return remainingChapters * 30; // 假设每章节需要30分钟
    }
}
-- 创建学习进度表
CREATE TABLE learning_progress (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id VARCHAR(100) NOT NULL COMMENT '用户ID',
    course_id VARCHAR(100) NOT NULL COMMENT '课程ID',
    course_name VARCHAR(200) NOT NULL COMMENT '课程名称',
    chapter_name VARCHAR(200) COMMENT '当前章节名称',
    total_chapters INT DEFAULT 0 COMMENT '总章节数',
    completed_chapters INT DEFAULT 0 COMMENT '已完成章节数',
    study_time_minutes INT DEFAULT 0 COMMENT '学习时长（分钟）',
    score INT DEFAULT 0 COMMENT '当前得分',
    status VARCHAR(50) DEFAULT 'in_progress' COMMENT '学习状态：in_progress, completed, paused',
    last_study_time DATETIME COMMENT '最后学习时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_course (user_id, course_id),
    INDEX idx_user_id (user_id),
    INDEX idx_course_id (course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习进度表';

-- 插入测试数据
INSERT INTO learning_progress (user_id, course_id, course_name, chapter_name, total_chapters, completed_chapters, study_time_minutes, score, status, last_study_time)
VALUES 
('user001', 'java-basic', 'Java基础课程', '第1章：Java入门', 10, 1, 60, 85, 'in_progress', NOW()),
('user002', 'spring-boot', 'Spring Boot实战', '第3章：Spring Boot配置', 15, 3, 180, 92, 'in_progress', NOW()),
('user001', 'mysql-advanced', 'MySQL高级课程', '第2章：索引优化', 12, 2, 120, 78, 'in_progress', NOW());
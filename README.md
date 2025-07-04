# 🚀 Heima AI - 智能学习助手平台

一个基于 Spring Boot 和 Spring AI 的智能教育平台，集成了多种 AI 功能，包括聊天对话、客服系统、学习进度追踪、PDF 问答和游戏化学习等。

## 🌟 主要功能

### 🤖 AI 聊天助手
- **多模态聊天**: 支持文本+图片的多模态对话
- **小团团助手**: 热心可爱的智能助手，提供个性化服务
- **流式响应**: 实时流式对话体验

### 🎓 智能客服系统
- **课程咨询**: 智能推荐适合的课程
- **预约服务**: 自动生成课程预约单
- **校区查询**: 查询可用校区信息
- **Function Calling**: 集成数据库操作工具

### 📚 PDF 智能问答
- **文档上传**: 支持 PDF 文档解析
- **RAG 问答**: 基于向量检索的智能问答
- **上下文理解**: 准确理解文档内容并回答问题

### 🎮 游戏化学习
- **哄女友游戏**: 有趣的角色扮演游戏
- **学习激励**: 通过游戏化提高学习兴趣

### 📊 MCP 学习进度追踪 (新功能)
- **进度记录**: 实时追踪学习进度
- **智能分析**: 学习效率和连续性分析
- **个性化建议**: 基于进度的学习建议
- **报告生成**: 详细的学习报告

## 🏗️ 技术架构

### 后端技术栈
- **Spring Boot 3.4.3**: 主框架
- **Spring AI**: AI 集成框架
- **MyBatis-Plus**: 数据访问层
- **MySQL**: 数据库
- **Java 17**: 编程语言

### AI 集成
- **阿里云 DashScope**: 主要 AI 服务提供商
- **OpenAI API**: 兼容的 API 接口
- **Ollama**: 本地 AI 模型支持
- **向量数据库**: 支持 RAG 检索

### MCP 协议
- **Model Context Protocol**: 标准化的 AI 工具交互协议
- **Spring AI MCP**: Spring AI 的 MCP 实现
- **工具集成**: 可扩展的 AI 工具系统

## 🚀 快速开始

### 环境要求
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Node.js (可选，用于前端)

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/Wilsoncyf/heima-ai.git
cd heima-ai
```

2. **数据库配置**
```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE itheima CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 导入数据表
mysql -u root -p itheima < create_learning_progress_table.sql
```

3. **配置文件**
```yaml
# 修改 src/main/resources/application.yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/itheima
    username: your_username
    password: your_password
  ai:
    openai:
      api-key: your_api_key
```

4. **启动应用**
```bash
# 编译项目
mvn clean compile

# 启动应用
mvn spring-boot:run

# 启用 MCP 服务器
mvn spring-boot:run -Dspring.profiles.active=mcp
```

## 📡 API 接口

### 聊天对话
```bash
# 基础聊天
curl "http://localhost:8080/ai/chat?prompt=你好&chatId=test001"

# 多模态聊天（带图片）
curl -X POST "http://localhost:8080/ai/chat" \
  -F "prompt=这是什么图片？" \
  -F "chatId=test001" \
  -F "files=@image.jpg"
```

### 客服系统
```bash
# 客服咨询
curl "http://localhost:8080/ai/service?prompt=我想学习Java课程&chatId=service001"
```

### 学习进度追踪
```bash
# 更新学习进度
curl -X POST "http://localhost:8080/ai/learning/progress" \
  -H "Content-Type: application/json" \
  -d '{"userId":"user001","courseId":"java-basic","courseName":"Java基础","chapterName":"第1章","studyTimeMinutes":30,"score":85,"status":"in_progress"}'

# 查询学习进度
curl "http://localhost:8080/ai/learning/progress/user001"

# 生成学习报告
curl "http://localhost:8080/ai/learning/report/user001"

# 智能学习对话
curl "http://localhost:8080/ai/learning/chat?prompt=我想看看我的学习进度&chatId=learning001"
```

## 🛠️ 开发指南

### 项目结构
```
heima-ai/
├── src/main/java/com/itheima/ai/
│   ├── config/          # 配置类
│   ├── controller/      # 控制器
│   ├── entity/          # 实体类
│   ├── service/         # 服务层
│   ├── mapper/          # 数据访问层
│   ├── tools/           # AI 工具类
│   ├── mcp/             # MCP 工具实现
│   └── utils/           # 工具类
├── src/main/resources/
│   ├── application.yaml # 配置文件
│   └── mapper/          # MyBatis 映射文件
└── src/test/            # 测试代码
```

### 添加新的 MCP 工具
1. 创建工具类并添加 `@McpTool` 注解
2. 在 `McpConfiguration` 中注册工具
3. 编写对应的测试用例

### 扩展 AI 功能
1. 继承现有的 `ChatClient` 配置
2. 添加自定义的 `@Tool` 方法
3. 配置相应的系统提示词

## 📊 MCP 工具功能

### 学习进度追踪工具
- `updateLearningProgress` - 更新学习进度
- `getLearningProgress` - 获取学习进度
- `generateLearningReport` - 生成学习报告
- `getStudySuggestions` - 获取学习建议
- `checkLearningStreak` - 检查学习连续性
- `calculateStudyEfficiency` - 计算学习效率

### 课程管理工具
- `queryCourse` - 查询课程信息
- `querySchool` - 查询校区信息
- `createCourseReservation` - 创建课程预约

## 🧪 测试

```bash
# 运行所有测试
mvn test

# 运行 MCP 功能测试
mvn test -Dtest=LearningProgressMcpToolTest
```

## 🔧 配置说明

### AI 模型配置
- **通用聊天**: qwen-omni-turbo (阿里云)
- **本地模型**: deepseek-r1:1.5b (Ollama)
- **嵌入模型**: text-embedding-v3

### MCP 服务器配置
- **传输协议**: STDIO
- **服务器名称**: heima-ai-mcp-server
- **版本**: 1.0.0

## 🤝 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

- [Spring AI](https://spring.io/projects/spring-ai) - AI 集成框架
- [阿里云 DashScope](https://dashscope.aliyun.com/) - AI 服务提供商
- [Model Context Protocol](https://modelcontextprotocol.io/) - MCP 协议标准

## 📞 联系方式

- 项目地址: https://github.com/Wilsoncyf/heima-ai
- 作者: Wilson
- 邮箱: [your-email@example.com]

---

⭐ 如果这个项目对你有帮助，请给个星星支持一下！
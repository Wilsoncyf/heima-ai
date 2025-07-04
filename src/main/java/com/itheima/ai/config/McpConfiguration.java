package com.itheima.ai.config;

import com.itheima.ai.mcp.LearningProgressMcpTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.mcp.server.McpSyncServer;
import org.springframework.ai.mcp.server.transport.stdio.StdioServerTransport;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class McpConfiguration {
    
    @Bean
    @Profile("mcp")
    public McpSyncServer mcpSyncServer(LearningProgressMcpTool learningProgressMcpTool) {
        return McpSyncServer.builder()
                .name("heima-ai-mcp-server")
                .version("1.0.0")
                .transport(new StdioServerTransport())
                .tools(learningProgressMcpTool)
                .build();
    }
    
    @Bean
    public ChatClient learningChatClient(OpenAiChatModel model, ChatMemory chatMemory, LearningProgressMcpTool learningProgressMcpTool) {
        return ChatClient
                .builder(model)
                .defaultSystem("""
                    你是一个智能学习助手，专门帮助用户跟踪和管理学习进度。你能够：
                    1. 记录和更新学习进度
                    2. 生成学习报告
                    3. 提供个性化的学习建议
                    4. 分析学习效率
                    5. 检查学习连续性
                    
                    请以友好、鼓励的语气与用户交流，帮助他们建立良好的学习习惯。
                    """)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .defaultTools(learningProgressMcpTool)
                .build();
    }
}
# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot AI application called "heima-ai" that provides various AI-powered services including:
- General chat with AI assistant (小团团)
- Customer service with function calling capabilities
- PDF document question-answering using RAG
- Game-related AI interactions
- Multi-modal chat (text + image)

## Tech Stack

- **Framework**: Spring Boot 3.4.3 with Java 17
- **AI Integration**: Spring AI framework with support for:
  - OpenAI API (via Alibaba DashScope)
  - Ollama for local models
- **Database**: MySQL with MyBatis-Plus
- **Vector Storage**: SimpleVectorStore for embeddings
- **Dependencies**: Lombok, Spring Web, PDF document reader

## Common Development Commands

```bash
# Run the application
mvn spring-boot:run

# Build the project
mvn clean compile

# Run tests
mvn test

# Package the application
mvn clean package
```

## Architecture

### Core Components

1. **Controllers** (`com.itheima.ai.controller`):
   - `ChatController`: General AI chat with multi-modal support
   - `CustomerServiceController`: Customer service with function calling
   - `GameController`: Game-related AI interactions
   - `PdfController`: PDF document Q&A
   - `ChatHistoryController`: Chat history management

2. **Configuration** (`com.itheima.ai.config`):
   - `CommonConfiguration`: Main bean configuration for ChatClient instances
   - `MvcConfiguration`: Web MVC configuration

3. **AI Model Integration**:
   - `AlibabaOpenAiChatModel`: Custom OpenAI chat model for Alibaba DashScope
   - Multiple ChatClient beans configured for different purposes:
     - `chatClient`: General chat with 小团团 persona
     - `serviceChatClient`: Customer service with CourseTools
     - `gameChatClient`: Game interactions
     - `pdfChatClient`: PDF Q&A with RAG

4. **Function Calling Tools**:
   - `CourseTools`: Provides course querying, school listing, and reservation creation

5. **Data Layer**:
   - MyBatis-Plus for database operations
   - Entities: Course, CourseReservation, School, Msg
   - Services and mappers following standard Spring architecture

### Key Features

- **Chat Memory**: InMemoryChatMemory for conversation context
- **Vector Search**: SimpleVectorStore with OpenAI embeddings for PDF Q&A
- **Function Calling**: Spring AI tools for database operations
- **Multi-modal Support**: Image + text processing
- **Streaming Responses**: Reactive streams for real-time chat

## Configuration

### AI Models
- **Default Chat Model**: qwen-omni-turbo (Alibaba DashScope)
- **Local Model**: deepseek-r1:1.5b (via Ollama)
- **Embedding Model**: text-embedding-v3 with 1024 dimensions

### Database
- MySQL connection configured for localhost:3306/itheima
- Default credentials: root/root (change for production)

## Development Notes

- API endpoints return streaming responses with `text/html;charset=utf-8`
- Chat history is managed per conversation ID
- System prompts are defined in `SystemConstants`
- Vector similarity threshold set to 0.6 for PDF Q&A
- Logging enabled for Spring AI and application debug
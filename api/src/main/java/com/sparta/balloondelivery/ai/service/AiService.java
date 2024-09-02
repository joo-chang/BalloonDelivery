package com.sparta.balloondelivery.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.balloondelivery.data.entity.AILog;
import com.sparta.balloondelivery.data.repository.AILogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AiService {

    private final RestTemplate restTemplate;
    private final AILogRepository aiLogRepository;
    private final ObjectMapper objectMapper;

    @Value("${ai.url}")
    private String url;

    @Autowired
    public AiService(RestTemplate restTemplate, AILogRepository aiLogRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.aiLogRepository = aiLogRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * AI 요청 처리 메서드
     * @param text 요청할 텍스트
     * @return AI 응답 텍스트
     */
    public String processAIRequest(String text) {
        // 입력 텍스트의 길이를 100자로 제한
        if (text.length() > 100) {
            text = text.substring(0, 100);
        }

        // 요청 텍스트에 "답변을 최대한 간결하게 50자 이하로" 추가
        String promptText = text + " 답변을 최대한 간결하게 50자 이하로";

        // 요청 본문 생성
        Map<String, Object> part = new HashMap<>();
        part.put("text", promptText);

        Map<String, Object> contents = new HashMap<>();
        contents.put("parts", new Map[]{part});

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", new Map[]{contents});

        // POST 요청 보내기
        String response = restTemplate.postForObject(url, requestBody, String.class);
        String contentResponse = "";

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode candidatesNode = root.path("candidates");
            if (candidatesNode.isArray() && candidatesNode.size() > 0) {
                JsonNode partsNode = candidatesNode.get(0).path("content").path("parts");
                if (partsNode.isArray() && partsNode.size() > 0) {
                    contentResponse = partsNode.get(0).path("text").asText();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 요청 및 응답 로그 저장
        AILog log = new AILog();
        log.setRequest(requestBody.toString());
        log.setResponse(contentResponse);
        aiLogRepository.save(log);

        // 응답 처리
        return contentResponse;
    }

    /**
     * 모든 AI 로그 조회
     * @return AI 로그 리스트
     */
    public List<AILog> getAllLogs() {
        return aiLogRepository.findAll();
    }

    /**
     * 특정 AI 로그 조회
     * @param logId 로그 ID
     * @return AI 로그 정보
     */
    public AILog getLogById(UUID logId) {
        return aiLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("AI 로그를 찾을 수 없습니다."));
    }

    /**
     * 특정 AI 로그 삭제
     * @param logId 로그 ID
     */
    public void deleteLog(UUID logId) {
        if (!aiLogRepository.existsById(logId)) {
            throw new RuntimeException("AI 로그를 찾을 수 없습니다.");
        }
        aiLogRepository.deleteById(logId);
    }
}

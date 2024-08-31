package com.sparta.balloondelivery.menu.service;

import com.sparta.balloondelivery.data.entity.AILog;
import com.sparta.balloondelivery.data.repository.AILogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MenuAIService {
    private final RestTemplate restTemplate;
    private final AILogRepository aiLogRepository;

    @Value("${ai.url}")
    private String url;

    @Autowired
    public MenuAIService(RestTemplate restTemplate, AILogRepository aiLogRepository) {
        this.restTemplate = restTemplate;
        this.aiLogRepository = aiLogRepository;
    }

    public String createMenuContents(String text) {
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

        // 응답이 50자를 넘는 경우, 잘라냄
        if (response != null && response.length() > 50) {
            response = response.substring(0, 50);
        }

        // 요청 및 응답 로그 저장
        AILog log = new AILog();
        log.setRequest(requestBody.toString());
        log.setResponse(response);
        aiLogRepository.save(log);

        // 응답 처리
        return response;
    }
}

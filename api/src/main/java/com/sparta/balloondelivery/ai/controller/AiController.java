package com.sparta.balloondelivery.ai.controller;

import com.sparta.balloondelivery.ai.service.AiService;
import com.sparta.balloondelivery.data.entity.AILog;
import com.sparta.balloondelivery.exception.BaseException;
import com.sparta.balloondelivery.util.ApiResponse;
import com.sparta.balloondelivery.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    @Autowired
    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    private void checkUserRole(String userRole, Set<String> allowRole) {
        if (!allowRole.contains(userRole)) {
            throw new BaseException(ErrorCode.NO_PERMISSION);
        }
    }

    /**
     * AI 요청을 처리하는 API
     * @param userRole 사용자 역할
     * @param request AI 요청 내용
     * @return AI 응답
     */
    @GetMapping("/{request}")
    public ApiResponse<String> getAIResponse(
            @RequestHeader("X-User-Role") String userRole,
            @PathVariable("request") String request
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER"));

        // AI 서비스에서 생성된 응답을 가져옵니다.
        String response = aiService.processAIRequest(request);

        // 응답 반환
        return ApiResponse.success("OK", response, "AI 요청 처리 성공");
    }

    /**
     * AI 로그 전체 조회 API
     * @param userRole 사용자 역할
     * @return AI 로그 리스트
     */
    @GetMapping("/logs")
    public ApiResponse<List<AILog>> getAILogs(
            @RequestHeader("X-User-Role") String userRole
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER", "OWNER"));

        List<AILog> logs = aiService.getAllLogs();
        return ApiResponse.success("OK", logs, "AI 로그 조회 성공");
    }

    /**
     * AI 로그 단일 조회 API
     * @param userRole 사용자 역할
     * @param logId 로그 ID
     * @return AI 로그 정보
     */
    @GetMapping("/logs/{logId}")
    public ApiResponse<AILog> getAILogById(
            @RequestHeader("X-User-Role") String userRole,
            @PathVariable("logId") UUID logId
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER"));

        AILog log = aiService.getLogById(logId);
        return ApiResponse.success("OK", log, "AI 로그 단일 조회 성공");
    }

    /**
     * AI 로그 삭제 API
     * @param userRole 사용자 역할
     * @param logId 로그 ID
     * @return 삭제 결과
     */
    @DeleteMapping("/logs/{logId}")
    public ApiResponse<String> deleteAILog(
            @RequestHeader("X-User-Role") String userRole,
            @PathVariable("logId") UUID logId
    ) {
        checkUserRole(userRole, Set.of("MASTER", "MANAGER"));

        aiService.deleteLog(logId);
        return ApiResponse.success("OK", "AI 로그 삭제 성공");
    }
}

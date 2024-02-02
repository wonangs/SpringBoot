package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 댓글 컨트롤러(댓글 REST API를 위한 컨트롤러)
@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // 1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {

        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleId);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments") // ------ ➊ 댓글 생성 요청 접수
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId,
                                             @RequestBody CommentDto dto) { // ---➋ create() 메서드 생성

        // 서비스에 위임
        CommentDto createDto = commentService.create(articleId, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createDto); // ---------------------------------------- ➌ null 반환
    }

    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}") // --------------------- ➊ 댓글 수정 요청 접수, 댓글의 id
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto dto) { // ---- ➋ update() 메서드 생성

        // 서비스에 위임
        CommentDto updatedDto = commentService.update(id, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto); // ------- ➌ null 반환
    }

    // 4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}") // --------------- ➊ 댓글 삭제 요청 접수
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) { // --- ➋ delete() 메서드 생성

        // 서비스에 위임
        CommentDto deletedDto = commentService.delete(id);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}

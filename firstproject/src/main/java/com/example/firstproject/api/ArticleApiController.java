package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {

        return articleRepository.findAll();

    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {

        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto) {

        Article article = dto.toEntity();
        return articleRepository.save(article);

    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) { // 반환형 수정

        // 1. DTO -> 엔티티 변환하기
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리하기
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString()); // 로그 출력
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // ResponseEntity 반환(Rest 컨트롤러 반환형)
        }

        // 4. 업데이트 및 정상 응답(200)하기
        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // article 엔티티 db에 저장
        return ResponseEntity.status(HttpStatus.OK).body(updated); // 정상 응답
    }


    // DELETE
    @DeleteMapping("/api/articles/{id}") // URL 요청 접수
    public ResponseEntity<Article> delete(@PathVariable Long id) { // 메서드 정의

        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
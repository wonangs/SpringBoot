package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
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
    private ArticleService articleService;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index() {

        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {

        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {

        Article created = articleService.create(dto);

        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) { // 반환형 수정

        Article update = articleService.update(id, dto); // 서비스를 통해 게시글 수정
        return (update != null) ? // 수정되면 정상, 안되면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(update) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // DELETE
    @DeleteMapping("/api/articles/{id}") // URL 요청 접수
    public ResponseEntity<Article> delete(@PathVariable Long id) { // 메서드 정의

        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test") // 여러 게시글 생성 요청 접수
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {

        List<Article> createList = articleService.createArticles(dtos); // 서비스 호출
        return (createList != null) ? // 생성 결과에 따라 응답 처리
                ResponseEntity.status(HttpStatus.OK).body(createList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


}
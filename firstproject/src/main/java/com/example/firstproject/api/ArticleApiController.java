package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Article update(@PathVariable Long id, @RequestBody ArticleForm dto) {

        // 1. DTO -> 엔티티 변환하기
        // 2. 타깃 조회하기
        // 3. 잘못된 요청 처리하기
        // 4. 업데이트 및 정상 응답(200)하기

    }

    // DELETE

}

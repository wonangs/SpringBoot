package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {

        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        // 로깅 코드로 대체
        log.info(article.toString());
//        System.out.println(article.toString());

        // 2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
        // 로깅 코드로 대체
        log.info(saved.toString());
//        System.out.println(saved.toString());

        return "";
        도라에몽 같이 놀아여 쟈ㅐ덜 ㅑㅐㄴ얼 ㅑㅐㅁ얼 ㅐㅑㅓㅇ래ㅑㅀㄹㄹ ㅓㅇ래히;ㅏ ㅓㅇ라ㅣㅎ ㅓ
    }
}

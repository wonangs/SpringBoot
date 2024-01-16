package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
@Slf4j
public class ArticleController {

    @Autowired // 자동 객체 생성 어노테이션
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아 오기
    // log.info("id = " + id);

        // 1. id를 조회해 데이터 가져오기
        // Article articleEntity = articleRepository.findById(id); findById의 반환형이 Article이 아니어서 생기는 오류

        // Optional<Article> articleEntity = articleRepository.findById(id); 해결방법 1

        // id 값으로 데이터를 찾을 때 해당 id 값이 없으면 null을 반환
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 반환하기
        return "articles/show"; // 목록으로 돌아가기 링크를 넣을 뷰 파일 확인
    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 1. 모든 데이터 가져오기
          ArrayList<Article> articleEntityList = articleRepository.findAll();
        //  findAll()의 반환 타입은 Iterable(Iterable(interface) ⊃ Collection(interface) ⊃ List(interface) ⊃ ArrayList(class))
        // a. 캐스팅 -> Iterable<Article>을 List<Article>로 다운캐스팅
        // b. articleEntityList 타입을 findAll() 메서드가 반환하는 타입으로 변환 -> List<Article>을 Iterable<Article>로 업캐스팅
        // c. ArrayList 이용

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
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

        return "redirect:/articles/" + saved.getId();
    }
}

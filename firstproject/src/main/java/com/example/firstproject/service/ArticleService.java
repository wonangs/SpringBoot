package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {

        return articleRepository.findAll();
    }

    public Article show(Long id) {

        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {

        Article article = dto.toEntity(); // dto -> 엔티티로 변환한 후 article에 저장
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article); // article을 db에 저장
    }

    public Article update(Long id, ArticleForm dto) {

        // 1. DTO -> 엔티티 변환하기
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 타깃 조회하기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리하기
        if (target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString()); // 로그 출력
            return null; // ResponseEntity 반환(Rest 컨트롤러 반환형) -> 컨트롤러 역할
        }

        // 4. 업데이트 및 정상 응답(200)하기
        target.patch(article); // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target); // article 엔티티 db에 저장
        return updated; // 정상 응답 -> 컨트롤러 역할
    }

    public Article delete(Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (target == null) { // 컨트롤러 역할
            return null;
        }

        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return target; // 삭제한 대상을 컨트롤러에 반환
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {

        // 1. dto 묶음(리스트)을 엔티티 묶음(리스트)으로 변환
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
        /* // 스트림 문법, 앞의 코드 해석
        * List<Article> articleList = new ArrayList<>();
        * for (int i = 0; i < dtos.size(); i++)
        *   ArticleForm dto = dtos.get(i);
        *   Article entity - dto.toEntity();
        *   articleList.add(entity);
        * */

        // 2. 엔티티 묶음(리스트)을 DB에 저장
        articleList.stream().forEach(article -> articleRepository.save(article));
        /* // 스트림 문법, 앞의 코드 해석
        * for (int i = 0; i < articleList.size(); i++)
        *   Article article = articleList.get(i);
        *   articleRepository.save(article);
        * */

        // 3. 강제 예외 발생
        // id가 -1인 데이터를 찾아서 찾는 데이터가 없으면 예외 발생
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        return articleList;
        

        // 4. 결과 값 반환
    }
}

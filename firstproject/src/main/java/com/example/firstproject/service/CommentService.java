package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// 댓글 서비스(컨트롤러와 리파지터리 사이에서 처리 흐름 및 트랜잭션 관리)
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {

        /*// 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 2. 엔티티 -> DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for (int i = 0; i < comments.size(); i++) { // --- ➊ 조회한 댓글 엔티티 수만큼 반복하기
            Comment c = comments.get(i); // --- ➋ 조회한 댓글 엔티티 하나씩 가져오기
            CommentDto dto = CommentDto.createCommentDto(c); // --- ➌ 엔티티를 DTO로 변환
            dtos.add(dto); // ----------------- ➍ 변환한 DTO를 dtos 리스트에 삽입
        }*/
        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId) // 댓글 엔티티 목록 조회
                .stream() // 댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDto.createCommentDto(comment)) // 엔티티를 DTO로 매핑
                .collect(Collectors.toList()); // 스트림 데이터를 리스트 자료형으로 변환
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {

        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId) // --- ➊ 부모 게시글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! " +
                        "대상 게시글이 없습니다.")); // -------------- ➋ 없으면 에러 메시지 출력

        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // 4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {

        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id) // --- ➊ 수정할 댓글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!" +
                        "대상 댓글이 없습니다.")); // -------- ➋ 없으면 에러 메시지 출력

        // 2. 댓글 수정
        target.patch(dto);

        // 3. DB로 갱신
        Comment updated = commentRepository.save(target);

        // 4. 댓글 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {

        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id) // --- ➊ 삭제할 댓글 가져오기
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상이 없습니다.")); // ------------- ➋ 없으면 에러 메시지 출력

        // 2. 댓글 삭제
        commentRepository.delete(target);

        // 3. 삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}

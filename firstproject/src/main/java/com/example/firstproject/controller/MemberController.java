package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signUpPage() {
        return "members/new";
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
    // log.info("id = " + id);

        Member member = memberRepository.findById(id).orElse(null);

        model.addAttribute("member", member);

        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {

        Iterable<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);

        return"members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        // 수정할 데이터 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기
        model.addAttribute("member", memberEntity);

        // 뷰 페이지 설정
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        log.info(form.toString());

        // 1. DTO를 엔티티로 변환
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());

        // 2. 엔티티를 DB에 저장
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);

        if (target != null) { // 기존 데이터가 존재한다면
            memberRepository.save(memberEntity); // 엔티티를 DB에 저장(갱신)
        }
        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/members/" + memberEntity.getId();
    }

    @PostMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다.");

        Member target = memberRepository.findById(id).orElse(null);
        log.info(target.toString());

        if (target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        return "/redirect:/members";
    }


    @PostMapping("/join")
    public String join(MemberForm memberForm) {

        log.info(memberForm.toString());

        Member member = memberForm.toEntity();
        log.info(member.toString());

        Member saved = memberRepository.save(member);
        log.info(saved.toString());

        return "redirect:/members/" + saved.getId();
    }
}



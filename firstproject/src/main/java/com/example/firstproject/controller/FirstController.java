package com.example.firstproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //메타데이터 - 컴파일 및 실행 과정에서 코드를 어떻게 처리할 지 알려주는 추가 정보
public class FirstController {


    @GetMapping("/hi")
    public String niceToMeetYou(Model model) { //model 객체 받아오기
        model.addAttribute("username", "영훈");
        return "greetings"; // greetings.mustache 파일 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "워낭");
        return "goodbye";
    }
}


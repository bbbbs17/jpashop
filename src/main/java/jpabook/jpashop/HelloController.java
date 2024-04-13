package jpabook.jpashop;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 사용자와 뷰 사이의 컨트롤 담당

public class HelloController {

    @GetMapping("hello") //url 뒤에 들어가는주소

   // Model객체 이용해서 사용
    public String hello(Model model){
        model.addAttribute("data", "hello!");
        return "hello"; // 실제로는 hello.html로 반환하는 문장

    }
}

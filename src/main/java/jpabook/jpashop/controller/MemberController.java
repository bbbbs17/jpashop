package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;


    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        //Model 객체는 Controller에서 생성된 데이터를 View로 전달할 때 사용하는 객체다.
        // 그럼 전달하는 방법은?
        return "members/createMemberForm";

    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){ // Binding result 는 오류를 담을수있음!

        if(result.hasErrors()) {
            return "members/createMemberForm"; // 에러가나면 첫화면으로 돌아감
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}

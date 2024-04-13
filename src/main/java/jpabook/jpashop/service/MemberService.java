package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true) // 읽기전용! 최적화임 , 반대로 입력할때는 쓰면안돼
// 디비 조회나 변경할때는 필수
 // @AllArgsConstructor 생성자를 따로 생성하지않아도됌
@RequiredArgsConstructor //final이 붙은 것을 생성자를 만들어줌
public class MemberService {


    private final MemberRepository memberRepository;

    //@Autowired // 생성자가 하나만있는경우 생략가능
    //public MemberService(MemberRepository memberRepository) {
      //  this.memberRepository = memberRepository;
    //}

    //회원가입
    @Transactional // 읽기전용이아니기떄문에 따로등록
    public Long join (Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member){
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }


    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //단건조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}

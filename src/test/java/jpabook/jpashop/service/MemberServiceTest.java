package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.hibernate.dialect.TiDBDialect;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional

public class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
 

    public void 회원가입() throws Exception {
       //given
        Member member = new Member();
        member.setName("Kong");
       //when
        Long saveId = memberService.join(member);


        //then
        assertEquals(member, memberRepository.findOne(saveId));

     }

     @Test(expected = IllegalStateException.class) //테스트코드에서 try catch문 생략가능 터지면 여기를통해 밖으로나감
     public void 중복_회원_예외() throws Exception {
        //given
         Member member1 = new Member();
         member1.setName("kim");

         Member member2 = new Member();
         member2.setName("kim");

        //when
       memberService.join(member1);
       memberService.join(member2);

        //then
         fail("예외가 발생해야한다.");

      }


}
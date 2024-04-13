package jpabook.jpashop.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 데이터베이스 접근
@RequiredArgsConstructor

public class MemberRepository {

   // @PersistenceContext // 엔티티매니저를 사용할때쓰는 어노테이션
    //스프링부트 jpa에서는 @persistenceContext를 @Autowired로 변경가능 ==>> final로 바꾸고 @RequiredArgsConstructor 대체가능
    private final EntityManager em;


    //저장
    public void save(Member member){
        em.persist(member);
    }


    //조회
    public Member findOne(Long id){
        return em.find(Member.class, id);  //jpa가 제공해주는 파인드 멤버클래스의 아이디를 찾아서 반환해줌
    }

    //리스트 조회

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); // 멤버의 m 에 m 을 반환? 한다는것같음 jpql간단하게 공부
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name" , name)
                .getResultList();
    }
}

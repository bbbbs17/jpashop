package jpabook.jpashop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // @Embedable 라는 곳에서 따로 객체로 묶음 만듬 거기서 끌어와서사용
    private Address address;

    @OneToMany(mappedBy = "member")
    //이때 멤버는 order테이블에있는 member이고
    //나는 주인이아니고 거울이에요 라는뜻(읽기전용)
    private List<Order> order = new ArrayList<>();

}

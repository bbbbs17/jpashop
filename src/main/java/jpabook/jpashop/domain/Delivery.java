package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;


    @Enumerated(EnumType.STRING) //enum타입을쓸때 ordenry쓰면 순서로 db저장 == 망함
    // 반드시 STRING 사용하여 이름그대로 저장
    private DeliberyStatus status; // READY COMP 배송준비 배송
}

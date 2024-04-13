package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Category;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter

public class Order {


    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memeber_id")
    private Member member;
      /*
        연관관계의 주인은 mappedby 를 사용하지 않는다.
        연관관계의 주인은 외래키가 있는 쪽이 주인이 된다.
        연관관계의 주인은 @JoinColumn사용한다. 이때 이름은 자기마음! 하지만
        보통 member_id처럼 쓰는게 관례
    */

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem>orderItems =new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    /*
        1:1 관계에서 외래키는 주테이블에 존재하는 것으로 하자. 그게 제일 편하다.
    */
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 오더의 시간 자바 8기능

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 order cancel


    //==연관관계 메서드

    public void setMember(Member member){
        this.member=member;
        member.getOrder().add(this);
    }

   // public static void main(String[] args) {
     //   Member member = new Member();
       // Order order = new Order();

        // member.getOrder().add(order); // 위의  member.getOrder().add(this); 사용하면서 안써도가능
    //    order.setMember(member);

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;


    }
    //==비지니스 로직==//

    /**
     * 주문취소
     */

    public void cancel(){
        if(delivery.getStatus() == DeliberyStatus.COMP){ //comp는 배송완료라는 뜻
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
    //==조회로직==//

    /**
     *전체 주문가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }



}

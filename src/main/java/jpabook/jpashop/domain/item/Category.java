package jpabook.jpashop.domain.item;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private  String name;

    @ManyToMany
    @JoinTable(name = "category_item" , //다대다 관계는 따로 일대다 다대일 테이블로 변형해줘야한다
            joinColumns = @JoinColumn(name ="category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") //계층구조에서의 내 부모확인
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();


    //==연관관계 메서드

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}



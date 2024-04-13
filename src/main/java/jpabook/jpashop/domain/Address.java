package jpabook.jpashop.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 묶어서 사용하기 편하게 분리해둠
@Getter
public class Address {


    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}

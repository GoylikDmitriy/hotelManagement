package com.goylik.hotelManagement.model.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Contacts {
    private String phone;
    private String email;

    @Override
    public String toString() {
        return "Contacts{" +
                "phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

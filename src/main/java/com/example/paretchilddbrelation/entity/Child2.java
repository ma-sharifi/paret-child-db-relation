package com.example.paretchilddbrelation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CHILD2")
@PrimaryKeyJoinColumn(name = "PARENT_ID")
@DiscriminatorValue("CHILD2")
@Getter
@Setter
@NoArgsConstructor
public class Child2 extends Parent {

    @Column(name = "AGE", nullable = false)
    private int age;
}

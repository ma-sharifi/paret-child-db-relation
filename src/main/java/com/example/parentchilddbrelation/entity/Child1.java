package com.example.parentchilddbrelation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CHILD1")
@PrimaryKeyJoinColumn(name = "PARENT_ID")
@DiscriminatorValue("CHILD1")
@Getter
@Setter
@NoArgsConstructor
public class Child1 extends Parent {

    @Column(name = "FAMILY", nullable = false)
    private String family;
}

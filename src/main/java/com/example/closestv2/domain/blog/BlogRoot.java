package com.example.closestv2.domain.blog;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "blog")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}

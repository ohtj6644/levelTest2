package com.ll.level2.Question;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String subject;

    @Column(columnDefinition = "text")
    private String content;

    private LocalDateTime createDate;
}

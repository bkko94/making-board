package com.study.board.entity;
//테이블 이름이랑 일치시켜라

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity  //JPA가 읽어들임.
@Data //롬복
public class Board {
//테이블 column들과 이름 똑같이

    @Id  //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //전략을 어떻게할까? -> identity는 mysql, mariadb에서 사용
    private Integer id;

    private String title;

    private String content;

    private String filename;

    private String filepath;
}

package com.qcenter.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    public List<Question> getQuestionList() {
        return questionList;
    }

    @ManyToMany
    private List<Question> questionList;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}

package com.qcenter.quizapp.repository;

import com.qcenter.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);
    List<Question> findByDifficultyLevel(String level);

    @Query(value = "Select * from question q where q.category=?1 Order By Random() Limit ?2", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int numQ);
}

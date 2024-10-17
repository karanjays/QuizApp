package com.qcenter.quizapp.service;

import com.qcenter.quizapp.model.Question;
import com.qcenter.quizapp.model.Quiz;
import com.qcenter.quizapp.model.QuizWrapper;
import com.qcenter.quizapp.model.Response;
import com.qcenter.quizapp.repository.QuestionDao;
import com.qcenter.quizapp.repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        if(numQ<10){
            numQ=10;
        }
        title=title + " " +  LocalDate.now();
        List<Question> questions=questionDao.findRandomQuestionByCategory(category.toUpperCase(),numQ);
        if(questions.isEmpty()){
            return new ResponseEntity<>("UnSuccess",HttpStatus.BAD_REQUEST);
        }
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuizWrapper>> getQuiz(int id) {
        Optional<Quiz> quiz= quizDao.findById(id);
        List<QuizWrapper> questionsForUser = new ArrayList<>();
        if(quiz.isEmpty())
            return new ResponseEntity<>(questionsForUser,HttpStatus.NOT_FOUND);
        List<Question> questionsFromDb = quiz.get().getQuestionList();
        for(Question q:questionsFromDb){
            QuizWrapper qw=new QuizWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {
        Optional<Quiz> quiz=quizDao.findById(id);
        if(quiz.isEmpty())
            return new ResponseEntity<>(0,HttpStatus.NOT_FOUND);
        List<Question> quizQuestions = quiz.get().getQuestionList();
        int ans=0;
        for(int i=0;i<quizQuestions.size();++i){
            if(responses.get(i).getResponse().equals(quizQuestions.get(i).getRightAnswer())){
                ++ans;
            }
        }
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }
}

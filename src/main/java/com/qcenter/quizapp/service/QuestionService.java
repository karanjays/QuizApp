package com.qcenter.quizapp.service;

import com.qcenter.quizapp.model.Question;
import com.qcenter.quizapp.repository.QuestionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    Logger logger = LoggerFactory.getLogger(QuestionService.class);
    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestion(){
        try{
            List<Question> questions = questionDao.findAll();
            if(questions.isEmpty())
                return new ResponseEntity<>(questions,HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(questions,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            List<Question> questions = questionDao.findByCategory(category);
            if(questions.isEmpty())
                return new ResponseEntity<>(questions,HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(questions,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByDifficultyLevel(String level) {
        try {
            List<Question> questions = questionDao.findByDifficultyLevel(level);
            if(questions.isEmpty())
                return new ResponseEntity<>(questions,HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(questions,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try{
//            if(questionDao.existsById(question.getId()))
//                return new ResponseEntity<>("Already Exist",HttpStatus.ALREADY_REPORTED);
            questionDao.save(question);
            logger.info(String.valueOf(question.getId()));
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("unsuccessful",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            if (questionDao.existsById(id)) {
                logger.info("id:-",questionDao.findById(id));
                questionDao.deleteById(id);
                return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return new ResponseEntity<>("Error occurred while deleting the question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        try{
            if(questionDao.existsById(question.getId())){
                questionDao.save(question);
                return new ResponseEntity<>("Question updated successfully",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Question not found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while updating the question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

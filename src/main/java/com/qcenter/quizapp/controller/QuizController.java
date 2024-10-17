package com.qcenter.quizapp.controller;

import com.qcenter.quizapp.model.QuizWrapper;
import com.qcenter.quizapp.model.Response;
import com.qcenter.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizServer;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizServer.createQuiz(category,numQ,title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuizWrapper>> getQuiz(@PathVariable int id){
        return quizServer.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> responses){
        return quizServer.calculateResult(id,responses);
    }
}

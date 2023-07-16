package com.example.math;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final Random random = new Random();
    private int max = 10;

    @GetMapping
    public List<Question> getQuestions(@RequestParam int amount) {
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int a = random.nextInt(max);
            int b = random.nextInt(max);

            Question question = Question.builder()
                    .question(String.format("%s + %s = ?", a, b))
                    .answer(String.valueOf(a + b))
                    .build();
            questions.add(question);
        }
        return questions;
    }
}

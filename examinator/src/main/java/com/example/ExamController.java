package com.example;

import com.example.model.Exam;
import com.example.model.Question;
import com.example.model.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/v1/exams")
@RequiredArgsConstructor
public class ExamController {

    private final RestTemplate restTemplate;

    @GetMapping
    public Exam getExam(@RequestBody Map<String, Integer> subjectToNumOfQuestionsMap) {
        Exam exam = new Exam();
        exam.setSubjects(new ArrayList<>());

        subjectToNumOfQuestionsMap.forEach((subject, numOfQuestions) -> {
            Question[] questionArray = restTemplate.getForObject(getServiceUrl(subject, numOfQuestions), Question[].class);
            List<Question> questionList = questionArray == null ? Collections.emptyList() : Arrays.asList(questionArray);

            exam.getSubjects().add(
                    Subject.builder()
                            .name(subject)
                            .questions(questionList)
                            .build()
            );
        });

        return exam;
    }

    private String getServiceUrl(String service, int numOfQuestions) {
        return String.format("http://%s/api/v1/questions?amount=%s", service, numOfQuestions);
    }
}

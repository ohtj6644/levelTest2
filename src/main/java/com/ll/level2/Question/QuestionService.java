package com.ll.level2.Question;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;


    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    Question article=new Question();

    public void create(String subject, String content) {

        article.setSubject(subject);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(article);


    }
    public int getCreateQuestionId() {
        return article.getId();
    }

    public Question getQuestion(Integer id) {
        return this.questionRepository.getById(id);
    }
}

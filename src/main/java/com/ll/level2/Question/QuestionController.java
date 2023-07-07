package com.ll.level2.Question;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String List(Model model){

        List<Question> questionList= this.questionService.getList();

        model.addAttribute("questionList", questionList);
        return "question_list";

    }

    @GetMapping("/question/create")
    public String create(QuestionForm questionForm){
        return "question_form";
    }

    @PostMapping("question/create")
    public String create(QuestionForm questionForm , BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list";
    }




}

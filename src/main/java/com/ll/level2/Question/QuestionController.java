package com.ll.level2.Question;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/article/list")
    public String List(Model model){

        List<Question> questionList= this.questionService.getList();

        model.addAttribute("questionList", questionList);
        return "question_list";

    }

    @GetMapping("/article/create")
    public String create(QuestionForm questionForm){
        return "question_form";
    }

    @PostMapping("article/create")
    public String create(QuestionForm questionForm , BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(),questionForm.getContent());
        int id= this.questionService.getCreateQuestionId();
        return String.format("redirect:/article/detail/%s", id);
    }

    @GetMapping(value = "article/detail/{id}")
    public  String detail(Model model , @PathVariable("id") Integer id){
        Question question= this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";

    }

    @GetMapping("/")
    public String main(){
        return "redirect:/article/list";
    }




}

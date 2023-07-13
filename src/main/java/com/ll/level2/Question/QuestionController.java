package com.ll.level2.Question;



import com.ll.level2.user.SiteUser;
import com.ll.level2.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

import java.util.List;
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/article/list")
    public String List(Model model, @RequestParam(value = "kw", defaultValue = "") String kw){

        List<Question> questionList= this.questionService.getList(kw);
        model.addAttribute("kw", kw);
        model.addAttribute("questionList", questionList);
        return "question_list";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/create")
    public String create(QuestionForm questionForm){
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("article/create")
    public String create(@Valid  QuestionForm questionForm , BindingResult bindingResult,Principal principal){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(),questionForm.getContent(),siteUser);
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



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/modify/{id}")
    public String articleModify(QuestionForm articleForm, @PathVariable("id") Integer id, Principal principal) {
        Question article = this.questionService.getQuestion(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/modify/{id}")
    public String articleModify(@Valid QuestionForm articleForm, BindingResult bindingResult,
                                Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question article = this.questionService.getQuestion(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(article, articleForm.getSubject(), articleForm.getContent());
        return String.format("redirect:/article/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/delete/{id}")
    public String articleDelete(Principal principal, @PathVariable("id") Integer id) {
        Question article = this.questionService.getQuestion(id);
        if (!article.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(article);
        return "redirect:/article/list";
    }




}

package com.ll.level2.Question;


import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 200)
    public String subject;

    public  String content;
}


package com.mysite.sbb.question;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
//@Getter, @Setter 합치면 Data
public class QuestionForm {
    @Size(max=200)
    @NotEmpty(message="제목은 필수항목입니다.")
    private String subject;

    @NotEmpty(message="내용은 필수항목입니다.")
    private String content;
}

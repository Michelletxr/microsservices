package com.br.servermessage.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailModel{

    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String emailFrom;
    @NotBlank
    @Email
    private String emailTo;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;
}
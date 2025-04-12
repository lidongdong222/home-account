package com.ldd.home.operate.entity.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class PasswordReq {
    @NotBlank(message = "oldPass cannot be empty!")
    private String oldPass;
    @NotBlank(message = "newPass cannot be empty!")
    @Length(min=8,max=20,message = "newPass length dose not meet the requirements!")
    private String newPass;
    @NotBlank(message = "newPassAgain cannot be empty!")
    @Length(min=8,max=20,message = "newPass length dose not meet the requirements!")
    private String newPassAgain;
}

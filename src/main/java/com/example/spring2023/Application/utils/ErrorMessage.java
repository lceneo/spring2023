package com.example.spring2023.Application.utils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ErrorMessage {

    public String createErrorMessage(BindingResult bindingResult) {
        var errList = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return String.join("\n", errList);
    }
}

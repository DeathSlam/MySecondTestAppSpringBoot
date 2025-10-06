package ru.tylyakov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.tylyakov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.tylyakov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.tylyakov.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;
    void isValidUid(Request request) throws UnsupportedCodeException;
}

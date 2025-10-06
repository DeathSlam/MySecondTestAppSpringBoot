package ru.tylyakov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.tylyakov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.tylyakov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.tylyakov.MySecondTestAppSpringBoot.model.Request;

@Service
public class RequestValidatorService implements ValidationService{

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException{
        if(bindingResult.hasErrors()){
            throw new
                    ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }

    @Override
    public void isValidUid(Request request) throws UnsupportedCodeException{
        if("123".equals(request.getUid())){
            throw new UnsupportedCodeException("UID 123 не поддерживается");
        }
    }
}

package ru.tylyakov.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tylyakov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.tylyakov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.tylyakov.MySecondTestAppSpringBoot.model.*;
import ru.tylyakov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.tylyakov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.tylyakov.MySecondTestAppSpringBoot.util.DataTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;

    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DataTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();

        try {
            validationService.isValid(bindingResult);
            validationService.isValidUid(request);
        } catch (ValidationFailedException e){
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("response: {}", response);
            log.error("ERROR: {},{}", response.getErrorCode(), response.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (UnsupportedCodeException e){
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("response: {}", response);
            log.error("ERROR: {},{}", response.getErrorCode(), response.getErrorMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("response: {}", response);
            log.error("ERROR: {},{}", response.getErrorCode(), response.getErrorMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        modifyResponseService.modify(response);
        log.info("response: {}", response);
        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }
}

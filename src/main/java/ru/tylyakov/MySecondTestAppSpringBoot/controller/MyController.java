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
import ru.tylyakov.MySecondTestAppSpringBoot.service.*;
import ru.tylyakov.MySecondTestAppSpringBoot.util.DataTimeUtil;

import java.util.Date;

@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final AnnualBonusService annualBonusService;
    private final QuarterBonusService quarterBonusService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService")
                        ModifyResponseService modifyResponseService,
                        ModifyRequestService modifyRequestService,
                        AnnualBonusService annualBonusService,
                        QuarterBonusService quarterBonusService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.annualBonusService = annualBonusService;
        this.quarterBonusService = quarterBonusService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {

        log.info("request: {}", request);

        Response response = buildBaseResponse(request);
        ResponseEntity<Response> errorResponse = validateRequest(bindingResult, request, response);

        if (errorResponse != null) {
            return errorResponse;
        }

        calculateAndSetBonuses(request, response);

        modifyRequestService.modify(request);
        Response modifiedResponse = modifyResponseService.modify(response);

        log.info("response: {}", modifiedResponse);
        return new ResponseEntity<>(modifiedResponse, HttpStatus.OK);
    }

    private Response buildBaseResponse(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DataTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

    private ResponseEntity<Response> validateRequest(BindingResult bindingResult,
                                                     Request request,
                                                     Response response) {
        try {
            validationService.isValid(bindingResult);
            validationService.isValidUid(request);
            return null;
        } catch (ValidationFailedException e) {
            return handleException(response, ErrorCodes.VALIDATION_EXCEPTION,
                    ErrorMessages.VALIDATION, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            return handleException(response, ErrorCodes.UNSUPPORTED_EXCEPTION,
                    ErrorMessages.UNSUPPORTED, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return handleException(response, ErrorCodes.UNKNOWN_EXCEPTION,
                    ErrorMessages.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Response> handleException(Response response,
                                                     ErrorCodes errorCode,
                                                     ErrorMessages errorMessage,
                                                     HttpStatus status) {
        response.setCode(Codes.FAILED);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);

        log.error("ERROR: {}, {}", errorCode, errorMessage);
        log.info("response: {}", response);

        return new ResponseEntity<>(response, status);
    }

    private void calculateAndSetBonuses(Request request, Response response) {
        response.setPosition(request.getPosition());
        response.setSalary(request.getSalary());
        response.setBonus(request.getBonus());
        response.setWorkDays(request.getWorkDays());

        double annualBonus = annualBonusService.calculate(
                request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays()
        );

        double quarterlyBonus = quarterBonusService.calculate(
                request.getPosition(),
                request.getSalary(),
                request.getBonus(),
                request.getWorkDays()
        );

        response.setAnnualBonus(annualBonus);
        response.setQuarterlyBonus(quarterlyBonus);
    }
}
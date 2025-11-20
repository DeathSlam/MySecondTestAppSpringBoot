package ru.tylyakov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.tylyakov.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);
}

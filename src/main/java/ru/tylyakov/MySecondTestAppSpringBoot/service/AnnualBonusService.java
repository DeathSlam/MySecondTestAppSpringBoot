package ru.tylyakov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.tylyakov.MySecondTestAppSpringBoot.model.Positions;

@Service
public interface AnnualBonusService {

    double calculate(Positions positions, double salary, double bonus, int workDays);
    double calculate(Positions position, double salary, double bonus, int workDays, int daysInYear);
}


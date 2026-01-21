package ru.tylyakov.MySecondTestAppSpringBoot.service;

import ru.tylyakov.MySecondTestAppSpringBoot.model.Positions;

public interface QuarterBonusService {
    double calculate(Positions position, double salary, double bonus, int workDays);
}
package ru.tylyakov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.tylyakov.MySecondTestAppSpringBoot.model.Positions;

@Service
public class QuarterBonusServiceImpl implements QuarterBonusService {

    @Override
    public double calculate(Positions position, double salary, double bonus, int workDays) {
        if (!position.isManager()) {
            return 0.0;
        }

        double quarterBonus = (salary * bonus * workDays * 0.25)
                / (90 * position.getPositionCoefficient());

        return Math.round(quarterBonus * 100.0) / 100.0;
    }
}
package ru.tylyakov.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tylyakov.MySecondTestAppSpringBoot.model.Positions;

import java.time.LocalDate;

@Slf4j
@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {

    @Override
    public double calculate(Positions position, double salary, double bonus, int workDays) {
        // Получаем текущий год и определяем количество дней
        int currentYear = LocalDate.now().getYear();
        int daysInYear = isLeapYear(currentYear) ? 366 : 365;

        return calculate(position, salary, bonus, workDays, daysInYear);
    }

    @Override
    public double calculate(Positions position, double salary, double bonus,
                            int workDays, int daysInYear) {
        // Добавляем логирование для отладки
        log.debug("Calculating annual bonus for position: {}, daysInYear: {}",
                position, daysInYear);

        double result = salary * bonus * workDays / (daysInYear * position.getPositionCoefficient());

        return Math.round(result * 100.0) / 100.0;
    }

    // Метод для определения високосного года
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Можно также добавить публичный метод для получения дней в году
    public int getDaysInYear(int year) {
        return isLeapYear(year) ? 366 : 365;
    }
}
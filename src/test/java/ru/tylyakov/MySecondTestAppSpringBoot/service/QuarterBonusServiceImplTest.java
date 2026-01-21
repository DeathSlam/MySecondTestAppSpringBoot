package ru.tylyakov.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tylyakov.MySecondTestAppSpringBoot.model.Positions;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuarterBonusServiceImplTest {

    @InjectMocks
    private QuarterBonusServiceImpl quarterBonusService;

    @Test
    void calculateForManagerShouldReturnPositiveValue() {
        double salary = 200000.0;
        double bonus = 2.0;
        int workDays = 90;

        double result = quarterBonusService.calculate(Positions.TL, salary, bonus, workDays);

        assertTrue(result > 0, "Для менеджера должен возвращаться положительный результат");
    }

    @Test
    void calculateForNonManagerShouldReturnZero() {
        double salary = 150000.0;
        double bonus = 1.8;
        int workDays = 90;

        double result = quarterBonusService.calculate(Positions.DEV, salary, bonus, workDays);

        assertEquals(0.0, result, "Для не-менеджера должен возвращаться 0");
    }

    @Test
    void calculateForDifferentManagersShouldReturnDifferentValues() {
        double salary = 200000.0;
        double bonus = 2.0;
        int workDays = 90;

        double resultTL = quarterBonusService.calculate(Positions.TL, salary, bonus, workDays);
        double resultPO = quarterBonusService.calculate(Positions.PO, salary, bonus, workDays);
        double resultCTO = quarterBonusService.calculate(Positions.CTO, salary, bonus, workDays);

        assertNotEquals(resultTL, resultPO);
        assertNotEquals(resultTL, resultCTO);
        assertNotEquals(resultPO, resultCTO);
    }

    @Test
    void calculateWithZeroWorkDaysShouldHandleGracefully() {
        double salary = 200000.0;
        double bonus = 2.0;
        int workDays = 0;

        double result = quarterBonusService.calculate(Positions.TL, salary, bonus, workDays);

        assertEquals(0.0, result);
    }

    @Test
    void calculateWithZeroSalaryShouldHandleGracefully() {
        double salary = 0.0;
        double bonus = 2.0;
        int workDays = 90;

        double result = quarterBonusService.calculate(Positions.TL, salary, bonus, workDays);

        assertEquals(0.0, result);
    }

    @Test
    void calculateWithZeroBonusShouldHandleGracefully() {
        double salary = 200000.0;
        double bonus = 0.0;
        int workDays = 90;

        double result = quarterBonusService.calculate(Positions.TL, salary, bonus, workDays);

        assertEquals(0.0, result);
    }

    @Test
    void calculateForAllManagerPositions() {
        double salary = 100000.0;
        double bonus = 1.5;
        int workDays = 90;

        double resultTL = quarterBonusService.calculate(Positions.TL, salary, bonus, workDays);
        double resultPO = quarterBonusService.calculate(Positions.PO, salary, bonus, workDays);
        double resultTPM = quarterBonusService.calculate(Positions.TPM, salary, bonus, workDays);
        double resultCTO = quarterBonusService.calculate(Positions.CTO, salary, bonus, workDays);

        assertTrue(resultTL > 0);
        assertTrue(resultPO > 0);
        assertTrue(resultTPM > 0);
        assertTrue(resultCTO > 0);
    }

    @Test
    void calculateForAllNonManagerPositions() {
        double salary = 100000.0;
        double bonus = 1.5;
        int workDays = 90;

        double resultDEV = quarterBonusService.calculate(Positions.DEV, salary, bonus, workDays);
        double resultHR = quarterBonusService.calculate(Positions.HR, salary, bonus, workDays);
        double resultQA = quarterBonusService.calculate(Positions.QA, salary, bonus, workDays);

        assertEquals(0.0, resultDEV);
        assertEquals(0.0, resultHR);
        assertEquals(0.0, resultQA);
    }

    @Test
    void calculateResultShouldBeRoundedToTwoDecimals() {
        double salary = 173456.78;
        double bonus = 1.75;
        int workDays = 88;

        double result = quarterBonusService.calculate(Positions.PO, salary, bonus, workDays);

        String resultStr = String.valueOf(result);
        int decimalPlaces = resultStr.length() - resultStr.indexOf('.') - 1;

        assertTrue(decimalPlaces <= 2, "Результат должен быть округлен до 2 знаков");
    }
}
package ru.tylyakov.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String uid; // Уникальный идентификатор сообщения
    private String operationUid; // Уникальный идентификатор операции
    private String systemTime; // Время формирования ответа
    private Codes code; // Код обработки запроса
    private ErrorCodes errorCode; // Код ошибки
    private ErrorMessages errorMessage; // Сообщение об ошибке
    private Positions position; // Должность сотрудника
    private Double salary; // Оклад сотрудника
    private Double bonus; // Коэффициент премии
    private Integer workDays; // Количество отработанных дней
    private Double annualBonus; // Годовая премия
    private Double quarterlyBonus; // Квартальная премия
}
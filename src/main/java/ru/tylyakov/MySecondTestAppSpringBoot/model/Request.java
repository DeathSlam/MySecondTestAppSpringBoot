package ru.tylyakov.MySecondTestAppSpringBoot.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Override
    public String toString(){
        return "{" + uid + '\'' +
                ", operationUid" + operationUid + '\'' +
                ", systemName='" + systemName + '\'' +
                ", source='" + source + '\'' +
                ", communicationId='" + communicationId + '\'' +
                ", templateId='" + templateId + '\'' +
                ", productCode='" + productCode + '\'' +
                ", smsCode='" + smsCode + '\'' +
                '}';

    }


    @NotBlank
    @Size(max = 32)
    private String uid; // Уникальный идентификатор сообщение
    @NotBlank
    @Size(max = 32)
    private String operationUid; // Уникальный идентификатор операции
    private Systems systemName; // Имя системы отправителя
    @NotBlank
    private String systemTime; // Время создания сообщения
    private String source; // Наименование ресурса
    private Positions position; // Должность сотрудника
    private Double salary; // Оклад сотрудника
    private Double bonus; // Коэффициент премии
    private Integer workDays; // Количество отработанных дней
    @Min(1)
    @Max(100000)
    private int communicationId; // Уникальный идентификатор коммуникации
    private int templateId; // Уникальный идентификатор шаблона
    private int productCode; // Код продукта
    private int smsCode; // Смс код
    private Long requestTime; // Время получения запроса (timestamp)


}
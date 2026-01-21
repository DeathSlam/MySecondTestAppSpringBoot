package ru.tylyakov.MySecondTestAppSpringBoot.model;

import lombok.Getter;

@Getter
public enum Positions {
    DEV(2.2, false),      // Разработчик
    HR(1.2, false),       // HR-специалист
    TL(2.6, true),        // Team Lead
    QA(1.8, false),       // Тестировщик
    PO(2.8, true),        // Product Owner (новый)
    TPM(2.4, true),       // Technical Product Manager (новый)
    CTO(3.0, true);       // Chief Technology Officer (новый)

    private final double positionCoefficient;
    private final boolean isManager;

    Positions(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }

    public boolean isManager() {
        return isManager;
    }
}
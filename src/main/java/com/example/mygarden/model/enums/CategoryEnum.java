package com.example.mygarden.model.enums;

public enum CategoryEnum {

    VEGETABLE ("Пипер, картофи, краставици, тиквички, различни сортове домати и др."),
    HOMEMADE ("Лично отгледани и консервирани зеленчуци."),
    OTHER("Ядки, пчелен мед и др."),

    SPICE("Сушени и пресни подправки");

    public final String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume("uuid_R6");
        Field field = r.getClass().getDeclaredFields()[0];

        field.setAccessible(true); // отражение, делаем приватные поля доступными для редактирования

        System.out.println(field.getName());
        System.out.println(field.get(r));

        field.set(r, "newUuid");
        System.out.println(r);
    }
}


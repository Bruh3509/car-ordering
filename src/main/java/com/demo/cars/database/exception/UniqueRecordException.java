package com.demo.cars.database.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true) // хотел еще добавить сюда же в аннотации accessLevel = Public, но что-то ругался, якобы не Public и не видно
public class UniqueRecordException extends Exception {
    public static String EMAIL_EXC = "email unique exception";
    public static String PHONE_EXC = "phone number unique exception";
    public static String PASSPORT_EXC = "passport id unique exception";
    public static String DR_LICENSE_EXC = "driving license id unique exception";

    public UniqueRecordException(String message) {
        super(message);
    }
}

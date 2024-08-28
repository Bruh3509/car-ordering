package com.demo.cars.util;

public final class PropertyUtil {
    // User
    public static final String EMAIL_EXC = "email unique exception";
    public static final String PHONE_EXC = "phone number unique exception";
    public static final String PASSPORT_EXC = "passport id unique exception";
    public static final String DR_LICENSE_EXC = "driving license id unique exception";

    // Place
    public static final int SRID = 4326;

    // Car
    public static final String PLATE_NUM_EXC = "plate number unique exception";

    // Stripe
    public static final String PAYMENT_SUCCESS_URL = "http://localhost:8080/payment";

    private PropertyUtil() {
    }
}

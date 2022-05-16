package uz.pdp.hrmanagementsystem.utils;

import java.util.UUID;


public class RandomUtils {
    public static String generateRandomPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 9);
    }
}

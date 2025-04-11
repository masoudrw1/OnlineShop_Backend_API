package com.masoud.comman.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashUtil {

        public static String hashWithSHA256(String input) {
            try {
                // ایجاد نمونه‌ای از SHA-256
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                // هش کردن داده‌ها
                byte[] hashBytes = digest.digest(input.getBytes());

                // تبدیل هش به فرمت هگزادسیمال
                StringBuilder hexString = new StringBuilder();
                for (byte b : hashBytes) {
                    hexString.append(String.format("%02x", b));
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error: SHA-256 Algorithm not found", e);
            }
        }

        public static void main(String[] args) {
            String input = "1381";
            String hashedValue = hashWithSHA256(input);
            System.out.println("SHA-256 Hash: " + hashedValue);
        }


}

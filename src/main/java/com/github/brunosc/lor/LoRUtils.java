package com.github.brunosc.lor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoRUtils {

    public static String padLeft(String input, String val, int length) {
        StringBuilder sb = new StringBuilder(input);
        while (sb.length() < length) {
            sb.insert(0, val);
        }
        return sb.toString();
    }

    public static String enumPrettyName(String name) {
        return Stream.of(name.split("_"))
                .map(String::toLowerCase)
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));
    }

}

package com.github.brunosc.lor.domain;

import com.github.brunosc.lor.LoRUtils;

import java.util.EnumSet;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public enum LoRRegion {
    DEMACIA("DE", 0),
    FRELJORD("FR", 1),
    IONIA("IO", 2),
    NOXUS("NX", 3),
    PILTOVER_AND_ZAUN("PZ", 4),
    SHADOW_ILES("SI", 5),
    BILGEWATER("BW", 6),
    MOUNT_TARGON("MT", 9);

    private final String code;
    private final int id;

    private static final List<LoRRegion> regions;

    static {
        regions = EnumSet.allOf(LoRRegion.class)
                .stream()
                .collect(toUnmodifiableList());
    }

    LoRRegion(String code, int id) {
        this.code = code;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public String prettyName() {
        return LoRUtils.enumPrettyName(this.name());
    }

    public static LoRRegion fromCode(String code) {
        return regions.stream()
                .filter(region -> region.getCode().equalsIgnoreCase(code))
                .findFirst().orElseThrow(() -> new RuntimeException("region does not exist!"));
    }

    public static LoRRegion fromId(int id) {
        return regions.stream()
                .filter(region -> region.getId() == id)
                .findFirst().orElseThrow(() -> new RuntimeException("region does not exist!"));
    }
}

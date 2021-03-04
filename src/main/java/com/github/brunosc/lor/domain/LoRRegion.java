package com.github.brunosc.lor.domain;

import com.github.brunosc.lor.LoRUtils;
import com.github.brunosc.lor.exception.RegionDoesNotExistException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum LoRRegion {
    DEMACIA("DE", 0),
    FRELJORD("FR", 1),
    IONIA("IO", 2),
    NOXUS("NX", 3),
    PILTOVER_AND_ZAUN("PZ", 4),
    SHADOW_ILES("SI", 5),
    BILGEWATER("BW", 6),
    SHURIMA("SH", 7),
    MOUNT_TARGON("MT", 9);

    private final String code;
    private final int id;

    private static final List<LoRRegion> regions;

    static {
        regions = new ArrayList<>(EnumSet.allOf(LoRRegion.class));
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
                .findFirst()
                .orElseThrow(() -> new RegionDoesNotExistException(code));
    }

    public static LoRRegion fromId(int id) {
        return regions.stream()
                .filter(region -> region.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RegionDoesNotExistException(id));
    }

}

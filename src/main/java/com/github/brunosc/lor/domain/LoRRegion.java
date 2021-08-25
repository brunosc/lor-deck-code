package com.github.brunosc.lor.domain;

import com.github.brunosc.lor.LoRUtils;
import com.github.brunosc.lor.exception.RegionDoesNotExistException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum LoRRegion {
    DEMACIA(1, "DE", 0),
    FRELJORD(1, "FR", 1),
    IONIA(1, "IO", 2),
    NOXUS(1, "NX", 3),
    PILTOVER_AND_ZAUN(1, "PZ", 4),
    SHADOW_ILES(1, "SI", 5),
    BILGEWATER(2, "BW", 6),
    SHURIMA(3, "SH", 7),
    MOUNT_TARGON(2, "MT", 9),
    BANDLE_CITY(4, "BC", 10);

    private final int version;
    private final String code;
    private final int id;

    private static final List<LoRRegion> regions;

    static {
        regions = new ArrayList<>(EnumSet.allOf(LoRRegion.class));
    }

    LoRRegion(int version, String code, int id) {
        this.version = version;
        this.code = code;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
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

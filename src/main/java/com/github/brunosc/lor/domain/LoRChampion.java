package com.github.brunosc.lor.domain;

import com.github.brunosc.lor.LoRUtils;

import java.util.EnumSet;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

public enum LoRChampion {

    FIZZ("02BW046"),
    TEEMO("01PZ008"),
    ZOE("03MT009"),
    DIANA("03MT056"),
    ELISE("01SI053"),
    LUCIAN("01DE022"),
    DRAVEN("01NX020"),
    EZREAL("01PZ036"),
    FIORA("01DE045"),
    KALISTA("01SI030"),
    KATARINA("01NX042"),
    LULU("03IO002"),
    MISS_FORTUNE("02BW022"),
    RIVEN("03NX007"),
    SORAKA("03MT055"),
    ZED("01IO009"),
    ASHE("01FR038"),
    BRAUM("01FR009"),
    JINX("01PZ040"),
    LEONA("03MT054"),
    MAOKAI("02SI008"),
    NOCTURNE("03SI005"),
    SHEN("01IO032"),
    SHYVANA("03DE011"),
    TAHM_KENCH("03BW004"),
    TARIC("03MT058"),
    TWISTED_FATE("02BW026"),
    VIKTOR("03PZ003"),
    YASUO("01IO015"),
    GANGPLANK("02BW032"),
    GAREN("01DE012"),
    HEIMERDINGER("01PZ056"),
    LEE_SIN("02IO006"),
    QUINN("02DE006"),
    THRESH("01SI052"),
    VLADIMIR("01NX006"),
    VI("02PZ008"),
    TRUNDLE("03FR006"),
    ANIVIA("01FR024"),
    DARIUS("01NX038"),
    HECARIM("01SI042"),
    KARMA("01IO041"),
    LUX("01DE042"),
    SEJUANI("02FR002"),
    NAUTILUS("02BW053"),
    TRYNDAMERE("01FR039"),
    AURELION_SOL("03MT087");

    private final String id;

    private static final List<LoRChampion> champions;

    static {
        champions = EnumSet.allOf(LoRChampion.class)
                .stream()
                .collect(toUnmodifiableList());
    }

    LoRChampion(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String prettyName() {
        return LoRUtils.enumPrettyName(this.name());
    }

    public static boolean isChampion(String cardCode) {
        return champions
                .stream()
                .anyMatch(c -> c.getId().equals(cardCode));
    }

    public static LoRChampion fromCardCode(String cardCode) {
        return champions
                .stream()
                .filter(c -> c.getId().equals(cardCode))
                .findFirst()
                .orElse(null);
    }

}

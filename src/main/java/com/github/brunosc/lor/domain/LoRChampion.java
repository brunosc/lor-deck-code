package com.github.brunosc.lor.domain;

import com.github.brunosc.lor.LoRUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
    SWAIN("02NX007"),
    AURELION_SOL("03MT087"),
    APHELIOS("03MT217"),
    LEBLANC("04NX004"),
    LISSANDRA("04FR005"),
    RENEKTON("04SH067"),
    SIVIR("04SH020"),
    KINDRED("04SI005"),
    TALIYAH("04SH073"),
    JARVAN_IV("04DE008"),
    NASUS("04SH047"),
    AZIR("04SH003"),
    ZILEAN("04SH039"),
    MALPHITE("04MT008"),
    IRELIA("04IO005"),
    EKKO("04PZ001"),
    PYKE("04BW005"),
    REK_SAI("04SH019"),
    AKSHAN("04SH130"),
    VIEGO("04SI055"),
    CAITLYN("05PZ006"),
    NAMI("05BW005"),
    TRISTANA("05BC133"),
    ZIGGS("05BC163"),
    POPPY("05BC041"),
    VEIGAR("05BC093"),
    XERATH("05SH014"),
    SENNA("05SI009"),
    SION("05NX001"),
    KENNEN("05BC058"),
    AHRI("05IO004"),
    PANTHEON("05MT003"),
    RUMBLE("05BC088"),
    JAYCE("05PZ022"),
    YUUMI("05BC029"),
    GNAR("05BC161"),
    UDYR("05FR013"),
    GALIO("05DE009");


    private final String id;

    private static final List<LoRChampion> champions;

    static {
        champions = new ArrayList<>(EnumSet.allOf(LoRChampion.class));
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

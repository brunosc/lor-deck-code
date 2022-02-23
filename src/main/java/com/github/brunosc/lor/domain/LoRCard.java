package com.github.brunosc.lor.domain;

import java.util.Objects;

import static com.github.brunosc.lor.LoRUtils.padLeft;

public class LoRCard {

    private final int set;
    private final LoRRegion region;
    private final int id;

    private LoRCard(String cardCode) {
        this.set = Integer.parseInt(cardCode.substring(0, 2));
        this.region = LoRRegion.fromCode(cardCode.substring(2, 4));
        this.id = Integer.parseInt(cardCode.substring(4, 7));
    }

    private LoRCard(int set, LoRRegion region, int id) {
        this.set = set;
        this.region = region;
        this.id = id;
    }

    public static LoRCard of(String setString, String regionString, String numberString) {
        try {
            int set     = Integer.parseInt(setString);
            LoRRegion region   = LoRRegion.fromCode(regionString);
            int        id      = Integer.parseInt(numberString);

            return new LoRCard(set, region, id);
        } catch (Exception e) {
            return null;
        }
    }

    public static LoRCard of(String cardCode) {
        if (cardCode.length() < 7) {
            return null;
        }

        return new LoRCard(cardCode);

    }

    public String getCardCode() {
        return padLeft(String.valueOf(set), "0", 2) + region.getCode() + padLeft(String.valueOf(id), "0", 3);
    }

    public int getSet() {
        return set;
    }

    public LoRRegion getRegion() {
        return region;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoRCard loRCard = (LoRCard) o;
        return set == loRCard.set &&
                id == loRCard.id &&
                region == loRCard.region;
    }

    @Override
    public int hashCode() {
        return Objects.hash(set, region, id);
    }
}

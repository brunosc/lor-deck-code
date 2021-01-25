package com.github.brunosc.lor.domain;

import java.util.Map;
import java.util.Set;

public class LoRDeck {

    private final Map<LoRCard, Integer> cards;
    private final Set<LoRRegion> regions;
    private final Set<LoRChampion> champions;

    public LoRDeck(Map<LoRCard, Integer> cards, Set<LoRRegion> regions, Set<LoRChampion> champions) {
        this.cards = cards;
        this.regions = regions;
        this.champions = champions;
    }

    public Map<LoRCard, Integer> getCards() {
        return cards;
    }

    public Set<LoRRegion> getRegions() {
        return regions;
    }

    public Set<LoRChampion> getChampions() {
        return champions;
    }
}

package com.github.brunosc.lor;

import com.github.brunosc.lor.domain.LoRCard;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRDeck;
import com.github.brunosc.lor.domain.LoRRegion;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

class DeckBuilder {

    private Map<LoRCard, Integer> cards = new HashMap<>();

    void addCard(LoRCard card, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be at least 1");
        }

        cards.put(card, count);
    }

    void clear() {
        cards = new HashMap<>();
    }

    LoRDeck toDomain() {
        return new LoRDeck(cards, regions(), champions());
    }

    private Set<LoRRegion> regions() {
        return cards.keySet().stream()
                .filter(Objects::nonNull)
                .map(LoRCard::getRegion)
                .collect(toSet());
    }

    private Set<LoRChampion> champions() {
        return cards.keySet().stream()
                .filter(Objects::nonNull)
                .map(LoRCard::getCardCode)
                .filter(LoRChampion::isChampion)
                .map(LoRChampion::fromCardCode)
                .collect(toSet());
    }

}

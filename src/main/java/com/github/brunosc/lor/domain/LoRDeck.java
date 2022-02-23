package com.github.brunosc.lor.domain;

import com.github.brunosc.lor.DeckCodeParser;

import java.util.HashMap;
import java.util.Map;

public class LoRDeck {

    private final Map<LoRCard, Integer> cards = new HashMap<>();

    public Map<LoRCard, Integer> getCards() {
        return cards;
    }

    public void addCard(LoRCard card, int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count must be at least 1");
        }
        cards.put(card, count);
    }

    public String getDeckCode() {
        return DeckCodeParser.encode(this);
    }

}

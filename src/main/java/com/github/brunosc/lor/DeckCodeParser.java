package com.github.brunosc.lor;

import com.github.brunosc.lor.domain.LoRCard;
import com.github.brunosc.lor.domain.LoRDeck;
import com.github.brunosc.lor.domain.LoRRegion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeckCodeParser {

    public static LoRDeck decode(String code) {
        LoRDeck deck = new LoRDeck();
        List<Byte> bytes = new ArrayList<>(Arrays.asList(Base32.decodeBoxed(code)));

        int firstByte = bytes.remove(0);
        int format = firstByte >>> 4;
        int version = firstByte & 0xF;

        int MAX_KNOWN_VERSION = Arrays.stream(LoRRegion.values()).mapToInt(LoRRegion::getVersion).max().orElse(1);
        if (version > MAX_KNOWN_VERSION) {
            throw new IllegalArgumentException("The provided code requires a higher version of this library; please update");
        }

        for (int i = 3; i > 0; i--) {
            int groupCount = VarInt.pop(bytes);

            for (int j = 0; j < groupCount; j++) {
                int itemCount = VarInt.pop(bytes);
                int set = VarInt.pop(bytes);
                int faction = VarInt.pop(bytes);

                for (int k = 0; k < itemCount; k++) {
                    int card = VarInt.pop(bytes);
                    String setString = LoRUtils.padLeft(String.valueOf(set), "0", 2);
                    String regionString = LoRRegion.fromId(faction).getCode();
                    String cardString = LoRUtils.padLeft(String.valueOf(card), "0", 3);

                    deck.addCard(LoRCard.of(setString, regionString, cardString), i);
                }
            }
        }

        while (bytes.size() > 0) {
            int count = VarInt.pop(bytes);
            int set = VarInt.pop(bytes);
            int faction = VarInt.pop(bytes);
            int number = VarInt.pop(bytes);

            String setString = LoRUtils.padLeft(String.valueOf(set), "0", 2);
            String factionString = LoRRegion.fromId(faction).getCode();
            String numberString = LoRUtils.padLeft(String.valueOf(number), "0", 3);

            deck.addCard(LoRCard.of(setString, factionString, numberString), count);
        }

        return deck;
    }

    public static String encode(LoRDeck deck) {
        List<Integer> result = new ArrayList<>();

        if (deck.getCards().containsKey(null)) {
            return null;
        }

        // format and version = 0001 0011
        int FORMAT = 1;
        int VERSION = deck.getCards().keySet().stream()
                .map(LoRCard::getRegion)
                .map(LoRRegion::getVersion)
                .max(Comparator.comparingInt(i -> i))
                .orElse(1);
        int formatAndVersion = ((FORMAT << 4) | (VERSION & 0xF));
        result.add(formatAndVersion);

        Map<Integer, List<Map.Entry<LoRCard, Integer>>> counts = deck.getCards().entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue));
        List<List<Map.Entry<LoRCard, Integer>>> grouped3 = groupByFactionAndSetSorted(counts.getOrDefault(3, new ArrayList<>()));
        List<List<Map.Entry<LoRCard, Integer>>> grouped2 = groupByFactionAndSetSorted(counts.getOrDefault(2, new ArrayList<>()));
        List<List<Map.Entry<LoRCard, Integer>>> grouped1 = groupByFactionAndSetSorted(counts.getOrDefault(1, new ArrayList<>()));
        List<Map.Entry<LoRCard, Integer>> nOfs = counts.entrySet().stream()
                .filter(e -> e.getKey() > 3)
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());

        result.addAll(encodeGroup(grouped3));
        result.addAll(encodeGroup(grouped2));
        result.addAll(encodeGroup(grouped1));
        result.addAll(encodeNofs(nOfs));

        return Base32.encodeBoxed(result);
    }

    private static List<Integer> encodeGroup(List<List<Map.Entry<LoRCard, Integer>>> group) {
        List<Integer> result = new ArrayList<>(VarInt.get(group.size()));

        for (List<Map.Entry<LoRCard, Integer>> list : group) {
            result.addAll(VarInt.get(list.size()));

            LoRCard firstCard = list.get(0).getKey();
            result.addAll(VarInt.get(firstCard.getSet()));
            result.addAll(VarInt.get(firstCard.getRegion().getId()));

            for (Map.Entry<LoRCard, Integer> cc : list) {
                result.addAll(VarInt.get(cc.getKey().getId()));
            }
        }

        return result;
    }

    private static List<Integer> encodeNofs(List<Map.Entry<LoRCard, Integer>> nOfs) {
        List<Integer> result = new ArrayList<>();

        for (Map.Entry<LoRCard, Integer> cc : nOfs) {
            result.addAll(VarInt.get(cc.getValue()));
            result.addAll(VarInt.get(cc.getKey().getSet()));
            result.addAll(VarInt.get(cc.getKey().getRegion().getId()));
            result.addAll(VarInt.get(cc.getKey().getId()));
        }

        return result;
    }

    private static List<List<Map.Entry<LoRCard, Integer>>> groupByFactionAndSetSorted(List<Map.Entry<LoRCard, Integer>> cards) {
        List<List<Map.Entry<LoRCard, Integer>>> result = new ArrayList<>();

        while (cards.size() > 0) {
            List<Map.Entry<LoRCard, Integer>> set = new ArrayList<>();

            Map.Entry<LoRCard, Integer> first = cards.remove(0);
            LoRCard firstCard = first.getKey();
            set.add(first);

            for (int i = cards.size() - 1; i >= 0; i--) {
                LoRCard compareCard = cards.get(i).getKey();

                if (firstCard.getSet() == compareCard.getSet() && firstCard.getRegion() == compareCard.getRegion()) {
                    set.add(cards.get(i));
                    cards.remove(i);
                }
            }

            result.add(set);
        }

        // sort outer list by size, then by inner list code, then sort inner list by code
        Comparator<List<Map.Entry<LoRCard, Integer>>> c  = Comparator.comparing(List::size);
        Comparator<List<Map.Entry<LoRCard, Integer>>> c2 = Comparator.comparing((List<Map.Entry<LoRCard, Integer>> a) -> a.get(0).getKey().getCardCode());
        result.sort(c.thenComparing(c2));
        for (List<Map.Entry<LoRCard, Integer>> group : result) {
            group.sort(Comparator.comparing(a -> a.getKey().getCardCode()));
        }

        return result;
    }


}

package com.github.brunosc.lor;

import com.github.brunosc.lor.domain.LoRCard;
import com.github.brunosc.lor.domain.LoRChampion;
import com.github.brunosc.lor.domain.LoRDeck;
import com.github.brunosc.lor.domain.LoRRegion;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class DeckCodeParserTest {

    @Test
    void encodingRecommendedDecks() {
        InputStream file = DeckCodeParserTest.class.getClassLoader().getResourceAsStream("testdata.txt");
        if (file == null) {
            throw new RuntimeException("Unable to load test file");
        }

        List<String> codes = new ArrayList<>();
        List<LoRDeck> decks = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                codes.add(line);

                DeckBuilder deckBuilder = new DeckBuilder();
                while (scanner.hasNextLine() && !(line = scanner.nextLine()).equalsIgnoreCase("")) {
                    String[] parts = line.split(":");
                    deckBuilder.addCard(LoRCard.of(parts[1]), Integer.parseInt(parts[0]));
                }

                decks.add(deckBuilder.toDomain());
            }
        }

        for (int i = 0; i < decks.size(); i++) {
            String encoded = DeckCodeParser.encode(decks.get(i));
            assertEquals(codes.get(i), encoded, "Decks are not equal");

            LoRDeck decoded = DeckCodeParser.decode(encoded);
            assertTrue(checkSameDeck(decks.get(i), decoded), "Did not produce same deck when re-coded");
        }
    }

    @Test
    void decode() {
        String deckCode = "CECAIAIDAIHSQNYDAMCQGBAGAMAQKJZVHAAQEAYDAMAQEAYEAEAQGJIBAMCQEAIBAEBS4";
        LoRDeck deck = DeckCodeParser.decode(deckCode);

        assertNotNull(deck);

        LoRChampion elise = deck.getChampions().stream().findFirst().orElse(null);
        assertNotNull(elise);
        assertEquals(elise.getId(), LoRChampion.ELISE.getId());
        assertEquals(elise.prettyName(), "Elise");

        Set<LoRRegion> regions = deck.getRegions();
        assertTrue(regions.contains(LoRRegion.NOXUS));
        assertTrue(regions.contains(LoRRegion.SHADOW_ILES));
    }

    @Test
    void decodeEncode() {
        String deckCode = "CIBAIAIFB4WDANQIAEAQGDAUDAQSIJZUAIAQCAIEAEAQKBIA";

        LoRDeck deck = DeckCodeParser.decode(deckCode);
        String  result = DeckCodeParser.encode(deck);

        assertEquals(deckCode, result);
    }

    @Test
    void smallDeck() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE002"), 3);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded), "Did not produce same deck when re-coded");
    }

    @Test
    void largeDeck() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE002"), 3);
        deckBuilder.addCard(LoRCard.of("01DE003"), 3);
        deckBuilder.addCard(LoRCard.of("01DE004"), 3);
        deckBuilder.addCard(LoRCard.of("01DE005"), 3);
        deckBuilder.addCard(LoRCard.of("01DE006"), 3);
        deckBuilder.addCard(LoRCard.of("01DE007"), 3);
        deckBuilder.addCard(LoRCard.of("01DE008"), 3);
        deckBuilder.addCard(LoRCard.of("01DE009"), 3);
        deckBuilder.addCard(LoRCard.of("01DE010"), 3);
        deckBuilder.addCard(LoRCard.of("01DE011"), 3);
        deckBuilder.addCard(LoRCard.of("01DE012"), 3);
        deckBuilder.addCard(LoRCard.of("01DE013"), 3);
        deckBuilder.addCard(LoRCard.of("01DE014"), 3);
        deckBuilder.addCard(LoRCard.of("01DE015"), 3);
        deckBuilder.addCard(LoRCard.of("01DE016"), 3);
        deckBuilder.addCard(LoRCard.of("01DE017"), 3);
        deckBuilder.addCard(LoRCard.of("01DE018"), 3);
        deckBuilder.addCard(LoRCard.of("01DE019"), 3);
        deckBuilder.addCard(LoRCard.of("01DE020"), 3);
        deckBuilder.addCard(LoRCard.of("01DE021"), 3);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void moreThan3Small() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE002"), 4);
        deckBuilder.addCard(LoRCard.of("02BW003"), 2);
        deckBuilder.addCard(LoRCard.of("02BW010"), 3);
        deckBuilder.addCard(LoRCard.of("01DE004"), 5);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void moreThan3Large() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE002"), 3);
        deckBuilder.addCard(LoRCard.of("01DE003"), 3);
        deckBuilder.addCard(LoRCard.of("01DE004"), 3);
        deckBuilder.addCard(LoRCard.of("01DE005"), 3);
        deckBuilder.addCard(LoRCard.of("01DE006"), 4);
        deckBuilder.addCard(LoRCard.of("01DE007"), 5);
        deckBuilder.addCard(LoRCard.of("01DE008"), 6);
        deckBuilder.addCard(LoRCard.of("01DE009"), 7);
        deckBuilder.addCard(LoRCard.of("01DE010"), 8);
        deckBuilder.addCard(LoRCard.of("01DE011"), 9);
        deckBuilder.addCard(LoRCard.of("01DE012"), 3);
        deckBuilder.addCard(LoRCard.of("01DE013"), 3);
        deckBuilder.addCard(LoRCard.of("01DE014"), 3);
        deckBuilder.addCard(LoRCard.of("01DE015"), 3);
        deckBuilder.addCard(LoRCard.of("01DE016"), 3);
        deckBuilder.addCard(LoRCard.of("01DE017"), 3);
        deckBuilder.addCard(LoRCard.of("01DE018"), 3);
        deckBuilder.addCard(LoRCard.of("01DE019"), 3);
        deckBuilder.addCard(LoRCard.of("01DE020"), 3);
        deckBuilder.addCard(LoRCard.of("01DE021"), 3);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void singleCard40() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE002"), 40);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void worstCaseLength() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE002"), 4);
        deckBuilder.addCard(LoRCard.of("01DE003"), 4);
        deckBuilder.addCard(LoRCard.of("01DE004"), 4);
        deckBuilder.addCard(LoRCard.of("01DE005"), 4);
        deckBuilder.addCard(LoRCard.of("01DE006"), 4);
        deckBuilder.addCard(LoRCard.of("01DE007"), 5);
        deckBuilder.addCard(LoRCard.of("01DE008"), 6);
        deckBuilder.addCard(LoRCard.of("01DE009"), 7);
        deckBuilder.addCard(LoRCard.of("01DE010"), 8);
        deckBuilder.addCard(LoRCard.of("01DE011"), 9);
        deckBuilder.addCard(LoRCard.of("01DE012"), 4);
        deckBuilder.addCard(LoRCard.of("01DE013"), 4);
        deckBuilder.addCard(LoRCard.of("01DE014"), 4);
        deckBuilder.addCard(LoRCard.of("01DE015"), 4);
        deckBuilder.addCard(LoRCard.of("01DE016"), 4);
        deckBuilder.addCard(LoRCard.of("01DE017"), 4);
        deckBuilder.addCard(LoRCard.of("01DE018"), 4);
        deckBuilder.addCard(LoRCard.of("01DE019"), 4);
        deckBuilder.addCard(LoRCard.of("01DE020"), 4);
        deckBuilder.addCard(LoRCard.of("01DE021"), 4);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void orderDoesNotMatter() {
        DeckBuilder deckBuilder = new DeckBuilder();

        deckBuilder.addCard(LoRCard.of("01DE002"), 1);
        deckBuilder.addCard(LoRCard.of("01DE003"), 2);
        deckBuilder.addCard(LoRCard.of("02DE003"), 3);
        LoRDeck deck = deckBuilder.toDomain();

        deckBuilder.clear();

        deckBuilder.addCard(LoRCard.of("01DE003"), 2);
        deckBuilder.addCard(LoRCard.of("02DE003"), 3);
        deckBuilder.addCard(LoRCard.of("01DE002"), 1);
        LoRDeck deck2 = deckBuilder.toDomain();

        String code  = DeckCodeParser.encode(deck);
        String code2 = DeckCodeParser.encode(deck2);

        assertEquals(code, code2);
    }

    @Test
    void orderDoesNotMatterMoreThan3() {
        DeckBuilder deckBuilder = new DeckBuilder();

        deckBuilder.addCard(LoRCard.of("01DE002"), 4);
        deckBuilder.addCard(LoRCard.of("01DE003"), 2);
        deckBuilder.addCard(LoRCard.of("02DE003"), 3);
        deckBuilder.addCard(LoRCard.of("01DE004"), 5);
        LoRDeck deck = deckBuilder.toDomain();

        deckBuilder.clear();

        deckBuilder.addCard(LoRCard.of("01DE004"), 5);
        deckBuilder.addCard(LoRCard.of("01DE003"), 2);
        deckBuilder.addCard(LoRCard.of("02DE003"), 3);
        deckBuilder.addCard(LoRCard.of("01DE002"), 4);
        LoRDeck deck2 = deckBuilder.toDomain();

        String code  = DeckCodeParser.encode(deck);
        String code2 = DeckCodeParser.encode(deck2);

        assertEquals(code, code2);
    }

    @Test
    void invalidDecks() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE02"), 1);

        LoRDeck deck = deckBuilder.toDomain();

        String deckCode = DeckCodeParser.encode(deck);
        assertNull(deckCode);

        try {
            deckBuilder.clear();
            deckBuilder.addCard(LoRCard.of("01DE002"), 0);
            DeckCodeParser.encode(deck);

            fail("Count is 0, so it shouldn't return a valid card");
        } catch (IllegalArgumentException e) {
            // ok
        }

        try {
            deckBuilder.clear();
            deckBuilder.addCard(LoRCard.of("01DE002"), -1);
            DeckCodeParser.encode(deck);

            fail("Count is less than 1, but didn't fail");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    @Test
    void invalidCodes() {
        String badNot32 = "This is not a card code. Dont @me";
        String bad32    = "ABCDEFG";
        String empty    = "";

        checkCode(badNot32);
        checkCode(bad32);
        checkCode(empty);
    }

    @Test
    void targon() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("01DE002"), 1);
        deckBuilder.addCard(LoRCard.of("03MT003"), 1);
        deckBuilder.addCard(LoRCard.of("03MT010"), 1);
        deckBuilder.addCard(LoRCard.of("02BW004"), 1);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void shurima() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of("04SH073"), 3);
        deckBuilder.addCard(LoRCard.of("04SH013"), 3);
        deckBuilder.addCard(LoRCard.of("04SH038"), 3);
        deckBuilder.addCard(LoRCard.of("04SH076"), 3);

        LoRDeck deck = deckBuilder.toDomain();

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void newShurimaChampions() {
        DeckBuilder deckBuilder = new DeckBuilder();
        deckBuilder.addCard(LoRCard.of(LoRChampion.TALIYAH.getId()), 3);
        deckBuilder.addCard(LoRCard.of(LoRChampion.SIVIR.getId()), 3);
        deckBuilder.addCard(LoRCard.of(LoRChampion.NASUS.getId()), 3);
        deckBuilder.addCard(LoRCard.of(LoRChampion.AZIR.getId()), 3);

        LoRDeck deck = deckBuilder.toDomain();

        assertNotNull(deck);

        assertTrue(deck.getChampions().contains(LoRChampion.TALIYAH));
        assertTrue(deck.getChampions().contains(LoRChampion.SIVIR));
        assertTrue(deck.getChampions().contains(LoRChampion.NASUS));
        assertTrue(deck.getChampions().contains(LoRChampion.AZIR));
    }

    private void checkCode(String code)
    {
        try
        {
            DeckCodeParser.decode(code);
            fail("Invalid code did not produce an error");
        } catch (IllegalArgumentException e)
        {
            // ok
        } catch (Exception e)
        {
            fail("Invalid code produced the wrong exception");
        }
    }

    private boolean checkSameDeck(LoRDeck a, LoRDeck b) {
        if (a.getCards().size() != b.getCards().size()) {
            return false;
        }

        for (LoRCard cardA : a.getCards().keySet()) {
            if (!b.getCards().containsKey(cardA)) {
                return false;
            } else {
                Integer cardACount = a.getCards().get(cardA);
                Integer cardBCount = b.getCards().get(cardA);

                if (cardACount == null) {
                    return false;
                }

                if (cardACount.intValue() != cardBCount.intValue()) {
                    return false;
                }
            }
        }

        return true;
    }

}
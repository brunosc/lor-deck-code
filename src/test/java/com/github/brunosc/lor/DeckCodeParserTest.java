package com.github.brunosc.lor;

import com.github.brunosc.lor.domain.LoRCard;
import com.github.brunosc.lor.domain.LoRDeck;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

                LoRDeck deck = new LoRDeck();
                while (scanner.hasNextLine() && !(line = scanner.nextLine()).equalsIgnoreCase("")) {
                    String[] parts = line.split(":");
                    deck.addCard(LoRCard.of(parts[1]), Integer.parseInt(parts[0]));
                }

                decks.add(deck);
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
    }

    @Test
    void smallDeck() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE002"), 3);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded), "Did not produce same deck when re-coded");
    }

    @Test
    void largeDeck() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE002"), 3);
        deck.addCard(LoRCard.of("01DE003"), 3);
        deck.addCard(LoRCard.of("01DE004"), 3);
        deck.addCard(LoRCard.of("01DE005"), 3);
        deck.addCard(LoRCard.of("01DE006"), 3);
        deck.addCard(LoRCard.of("01DE007"), 3);
        deck.addCard(LoRCard.of("01DE008"), 3);
        deck.addCard(LoRCard.of("01DE009"), 3);
        deck.addCard(LoRCard.of("01DE010"), 3);
        deck.addCard(LoRCard.of("01DE011"), 3);
        deck.addCard(LoRCard.of("01DE012"), 3);
        deck.addCard(LoRCard.of("01DE013"), 3);
        deck.addCard(LoRCard.of("01DE014"), 3);
        deck.addCard(LoRCard.of("01DE015"), 3);
        deck.addCard(LoRCard.of("01DE016"), 3);
        deck.addCard(LoRCard.of("01DE017"), 3);
        deck.addCard(LoRCard.of("01DE018"), 3);
        deck.addCard(LoRCard.of("01DE019"), 3);
        deck.addCard(LoRCard.of("01DE020"), 3);
        deck.addCard(LoRCard.of("01DE021"), 3);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void moreThan3Small() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE002"), 4);
        deck.addCard(LoRCard.of("02BW003"), 2);
        deck.addCard(LoRCard.of("02BW010"), 3);
        deck.addCard(LoRCard.of("01DE004"), 5);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void moreThan3Large() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE002"), 3);
        deck.addCard(LoRCard.of("01DE003"), 3);
        deck.addCard(LoRCard.of("01DE004"), 3);
        deck.addCard(LoRCard.of("01DE005"), 3);
        deck.addCard(LoRCard.of("01DE006"), 4);
        deck.addCard(LoRCard.of("01DE007"), 5);
        deck.addCard(LoRCard.of("01DE008"), 6);
        deck.addCard(LoRCard.of("01DE009"), 7);
        deck.addCard(LoRCard.of("01DE010"), 8);
        deck.addCard(LoRCard.of("01DE011"), 9);
        deck.addCard(LoRCard.of("01DE012"), 3);
        deck.addCard(LoRCard.of("01DE013"), 3);
        deck.addCard(LoRCard.of("01DE014"), 3);
        deck.addCard(LoRCard.of("01DE015"), 3);
        deck.addCard(LoRCard.of("01DE016"), 3);
        deck.addCard(LoRCard.of("01DE017"), 3);
        deck.addCard(LoRCard.of("01DE018"), 3);
        deck.addCard(LoRCard.of("01DE019"), 3);
        deck.addCard(LoRCard.of("01DE020"), 3);
        deck.addCard(LoRCard.of("01DE021"), 3);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void singleCard40() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE002"), 40);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void worstCaseLength() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE002"), 4);
        deck.addCard(LoRCard.of("01DE003"), 4);
        deck.addCard(LoRCard.of("01DE004"), 4);
        deck.addCard(LoRCard.of("01DE005"), 4);
        deck.addCard(LoRCard.of("01DE006"), 4);
        deck.addCard(LoRCard.of("01DE007"), 5);
        deck.addCard(LoRCard.of("01DE008"), 6);
        deck.addCard(LoRCard.of("01DE009"), 7);
        deck.addCard(LoRCard.of("01DE010"), 8);
        deck.addCard(LoRCard.of("01DE011"), 9);
        deck.addCard(LoRCard.of("01DE012"), 4);
        deck.addCard(LoRCard.of("01DE013"), 4);
        deck.addCard(LoRCard.of("01DE014"), 4);
        deck.addCard(LoRCard.of("01DE015"), 4);
        deck.addCard(LoRCard.of("01DE016"), 4);
        deck.addCard(LoRCard.of("01DE017"), 4);
        deck.addCard(LoRCard.of("01DE018"), 4);
        deck.addCard(LoRCard.of("01DE019"), 4);
        deck.addCard(LoRCard.of("01DE020"), 4);
        deck.addCard(LoRCard.of("01DE021"), 4);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void orderDoesNotMatter() {
        LoRDeck deck1 = new LoRDeck();
        deck1.addCard(LoRCard.of("01DE002"), 1);
        deck1.addCard(LoRCard.of("01DE003"), 2);
        deck1.addCard(LoRCard.of("02DE003"), 3);

        LoRDeck deck2 = new LoRDeck();
        deck2.addCard(LoRCard.of("01DE003"), 2);
        deck2.addCard(LoRCard.of("02DE003"), 3);
        deck2.addCard(LoRCard.of("01DE002"), 1);

        String code  = DeckCodeParser.encode(deck1);
        String code2 = DeckCodeParser.encode(deck2);

        assertEquals(code, code2);
    }

    @Test
    void orderDoesNotMatterMoreThan3() {
        LoRDeck deck1 = new LoRDeck();
        deck1.addCard(LoRCard.of("01DE002"), 4);
        deck1.addCard(LoRCard.of("01DE003"), 2);
        deck1.addCard(LoRCard.of("02DE003"), 3);
        deck1.addCard(LoRCard.of("01DE004"), 5);

        LoRDeck deck2 = new LoRDeck();
        deck2.addCard(LoRCard.of("01DE004"), 5);
        deck2.addCard(LoRCard.of("01DE003"), 2);
        deck2.addCard(LoRCard.of("02DE003"), 3);
        deck2.addCard(LoRCard.of("01DE002"), 4);


        String code  = DeckCodeParser.encode(deck1);
        String code2 = DeckCodeParser.encode(deck2);

        assertEquals(code, code2);
    }

    @Test
    void invalidDecks() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE02"), 1);

        String deckCode = DeckCodeParser.encode(deck);
        assertNull(deckCode);

        try {
            LoRDeck deckWithZero = new LoRDeck();
            deckWithZero.addCard(LoRCard.of("01DE002"), 0);
            DeckCodeParser.encode(deck);

            fail("Count is 0, so it shouldn't return a valid card");
        } catch (IllegalArgumentException e) {
            // ok
        }

        try {
            LoRDeck deckWithNegative = new LoRDeck();
            deckWithNegative.addCard(LoRCard.of("01DE002"), -1);
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
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01DE002"), 1);
        deck.addCard(LoRCard.of("03MT003"), 1);
        deck.addCard(LoRCard.of("03MT010"), 1);
        deck.addCard(LoRCard.of("02BW004"), 1);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void shurima() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("04SH073"), 3);
        deck.addCard(LoRCard.of("04SH013"), 3);
        deck.addCard(LoRCard.of("04SH038"), 3);
        deck.addCard(LoRCard.of("04SH076"), 3);

        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    @Test
    void bundleCity() {
        LoRDeck deck = new LoRDeck();
        deck.addCard(LoRCard.of("01BC002"), 4);
        String  code    = DeckCodeParser.encode(deck);
        LoRDeck decoded = DeckCodeParser.decode(code);

        assertTrue(checkSameDeck(deck, decoded));
    }

    private void checkCode(String code) {
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
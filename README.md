lor-deck-code
============

[![](https://jitpack.io/v/brunosc/lor-deck-code.svg)](https://jitpack.io/#brunosc/lor-deck-code)

The lor-deck-code library can be used to encode/decode Legends of Runeterra decks to/from simple strings.

## How to use it

```
String deckCode = "CIBAIAIFB4WDANQIAEAQGDAUDAQSIJZUAIAQCAIEAEAQKBIA";

LoRDeck deck = DeckCodeParser.decode(deckCode);
```

### Domain

```
public class LoRDeck {
    private final Map<LoRCard, Integer> cards;
    private final Set<LoRRegion> regions;
    private final Set<LoRChampion> champions;    
}
```

```
public class LoRCard {
    private final int set;
    private final LoRRegion region;
    private final int id;
}
```

`LoRRegion` and `LoRChampion` are enums.

## Dependency

### Maven

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.brunosc.lor</groupId>
        <artifactId>lor-deck-code</artifactId>
        <version>0.0.1</version>
    </dependency>
</dependencies>
```

### Gradle

``` bash
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  implementation 'com.github.brunosc.lor:lor-deck-code:0.0.1'
}
```
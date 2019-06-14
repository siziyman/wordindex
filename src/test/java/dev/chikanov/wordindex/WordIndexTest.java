package dev.chikanov.wordindex;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: siziyman
 * Date: 14-Jun-19.
 */
class WordIndexTest {
    private static final String emptyFile ="src/test/resources/emptyFile";
    private static final String filledTestFile = "src/test/resources/testFile";
    private static final Set<String> words = new HashSet<>(Arrays.asList("мама", "рама", "мыла", "папа", "дед", "-1", "$$$", "===", "-1мама$", "дедед", "МаМа", "мАмА"));
    private static final Map<String, Set<Integer>> wordIndexPrecomputed = new HashMap<>();
    private WordIndex wordIndex;

    @BeforeAll
    static void mapSetup() {
        wordIndexPrecomputed.put("мама", new HashSet<>(Set.of(0, 34, 74, 100)));
        wordIndexPrecomputed.put("МаМа", new HashSet<>(Set.of(117)));
        wordIndexPrecomputed.put("мАмА", new HashSet<>(Set.of(122)));
        wordIndexPrecomputed.put("рама", new HashSet<>(Set.of(5, 15, 51)));
        wordIndexPrecomputed.put("мыла", new HashSet<>(Set.of(10)));
        wordIndexPrecomputed.put("папа", new HashSet<>(Set.of(20, 46)));
        wordIndexPrecomputed.put("дед", new HashSet<>(Set.of(25, 39, 56, 60, 64)));
        wordIndexPrecomputed.put("дедед", new HashSet<>(Set.of(68)));
        wordIndexPrecomputed.put("-1", new HashSet<>(Set.of(79)));
        wordIndexPrecomputed.put("$$$", new HashSet<>(Set.of(82)));
        wordIndexPrecomputed.put("===", new HashSet<>(Set.of(105)));
        wordIndexPrecomputed.put("-1мама$", new HashSet<>(Set.of(109)));

    }

    @BeforeEach
    void setup() {
        wordIndex = new WordIndex();
    }

    @Test
    void emptyFileTest() {
        assertThrows(IllegalArgumentException.class, () -> wordIndex.loadFile(emptyFile));
    }

    // Used manually precomputed data here, could be replaced with slow, but automatic process
    // (but it adds code as point of failure, although makes test data easier to modify)
    @Test
    void smokeTest() {
        wordIndex.loadFile(filledTestFile);
        words.forEach(word -> assertEquals((wordIndex.getIndexes4Word(word)), wordIndexPrecomputed.get(word)));
    }

}
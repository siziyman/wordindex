package dev.chikanov.wordindex;

import dev.chikanov.wordindex.tree.WordTrie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Author: chikanov
 * Date: 13-Jun-19.
 */
public class WordIndex {

    private WordTrie trie;

    public void loadFile(String filename) {
        trie = new WordTrie();
        File fileToLoad = new File(filename);
        if (!fileToLoad.exists() || !fileToLoad.isFile()) {
            throw new IllegalArgumentException("File does not exist or " + filename + " is not a file");
        }
        if (!fileToLoad.canRead()) {
            throw new IllegalArgumentException("Cannot read file");
        }
        FileInputStream fis;


        try {
            fis = new FileInputStream(fileToLoad);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("this shouldn't really happen after previous checks, but okay");
        }

        loopReadFile(fis);

    }

    private void loopReadFile(FileInputStream fis) {
        boolean atLeastOneWordExists = false;
        int idx = 0;
        InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder wordBuilder = new StringBuilder();
        try {
            int character;
            character = reader.read();
            while (character != -1) {
                char currentChar = (char) character;
                if (Character.isWhitespace(currentChar)) {
                    if (wordBuilder.length() != 0) {
                        trie.appendWord(wordBuilder.toString(), idx - wordBuilder.length());
                        atLeastOneWordExists = true;
                        // could use setLength(0) instead of this one, but not sure which's faster in the long run;
                        // this is probably faster, but consumes more memory (GC might become an issue?)
                        wordBuilder = new StringBuilder();
                    }
                    character = reader.read();
                } else {
                    wordBuilder.append(currentChar);
                    character = reader.read();
                }
                idx++;
            }
            if (wordBuilder.length() != 0) {
                atLeastOneWordExists = true;
                trie.appendWord(wordBuilder.toString(), idx - wordBuilder.length());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        if (!atLeastOneWordExists) {
            throw new IllegalArgumentException("File does not contain non-whitespace characters.");
        }
    }

    public Set<Integer> getIndexes4Word(String searchWord) {
        if (searchWord == null || searchWord.isEmpty() || searchWord.trim().isEmpty()) {
            // Could also throw IllegalArgument exception, but _technically_ it's the word which isn't really present
            // (except for whitespace-only ones) in a file, therefore I decided to stick to spec and return null.
            return null;
        }

        return trie.getIndexes4Word(searchWord);
    }
}

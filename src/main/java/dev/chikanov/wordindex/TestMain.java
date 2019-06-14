package dev.chikanov.wordindex;

/**
 * Author: chikanov
 * Date: 14-Jun-19.
 */
public class TestMain {
    public static void main(String[] args) {
        WordIndex index = new WordIndex();
        index.loadFile("nonExistentFile");
    }
}

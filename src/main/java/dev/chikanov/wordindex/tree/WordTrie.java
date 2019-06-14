package dev.chikanov.wordindex.tree;


import java.util.Map;
import java.util.Set;

/**
 * Author: chikanov
 * Date: 13-Jun-19.
 */
public class WordTrie {

    private final TrieNode root;

    public WordTrie() {
        this.root = new TrieNode();
    }


    // trie insertion
    public void appendWord(String word, int index) {
        if (word == null || word.trim().isEmpty()) {
            return;
        }
        TrieNode node = insertWordPath(word);
        node.addIndex(index);
    }

    // trie lookup
    public Set<Integer> getIndexes4Word(String searchWord) {
        TrieNode node = findWordNode(searchWord);
        if (node == null) {
            return null;
        }
        return node.getIndices();
    }

    private TrieNode insertWordPath(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            Map<Character, TrieNode> children = currentNode.getChildren();
            if (children == null) {
                currentNode = currentNode.appendChild(currentChar);
            } else {
                TrieNode child = children.get(currentChar);
                if (child == null) {
                    currentNode = currentNode.appendChild(currentChar);
                } else {
                    currentNode = child;
                }
            }


        }
        return currentNode;
    }


    //non-modifying tree lookup
    private TrieNode findWordNode(String searchWord) {
        TrieNode currentNode = root;
        for (int i = 0; i < searchWord.length(); i++) {
            char currentChar = searchWord.charAt(i);
            Map<Character, TrieNode> children = currentNode.getChildren();
            if (children == null) {
                return null;
            }
            currentNode = children.get(currentChar);
            if (currentNode == null) {
                return null;
            }
        }
        return currentNode;
    }
}

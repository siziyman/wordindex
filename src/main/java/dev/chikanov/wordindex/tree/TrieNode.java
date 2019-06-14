package dev.chikanov.wordindex.tree;

import java.util.*;

/**
 * Author: chikanov
 * Date: 13-Jun-19.
 */
public class TrieNode {

    private Character keyChar;
    private Map<Character, TrieNode> children;
    private Set<Integer> indices;

    TrieNode() {
        this.keyChar = null;
    }

    public TrieNode(char keyChar) {
        // Due to lack of details it's suggested that words will be separated by whitespace-class characters.
        if (Character.isWhitespace(keyChar)) {
            throw new IllegalArgumentException("Whitespace characters are not supported");
        }
        this.keyChar = keyChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrieNode trieNode = (TrieNode) o;
        return keyChar == trieNode.keyChar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyChar);
    }

    public char getKeyChar() {
        return keyChar;
    }

    public Set<Integer> getIndices() {
        return indices;

    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean addIndex(Integer index) {
        if (index == null || index < 0) {
            throw new IllegalArgumentException("File word offset cannot be " + index);
        }
        if (indices == null) {
            // Considering that spec does not require to return _ordered_ set of indices, I'm sticking to faster HashSet
            // instead of TreeSet, which would be preferable for ordered storage & return.
            indices = new HashSet<>();
        }
        return indices.add(index);
    }

    public boolean isLeaf() {
        return (children == null || children.isEmpty());
    }

    public TrieNode getToChild(char key) {
        return children.get(key);
    }

    TrieNode appendChild(char key) {
        if (children == null) {
            children = new HashMap<>();
        }

        children.computeIfAbsent(key, TrieNode::new);
        return children.get(key);
    }


}

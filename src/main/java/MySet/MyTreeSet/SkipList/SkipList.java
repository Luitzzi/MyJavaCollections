package MySet.MyTreeSet.SkipList;

import java.lang.reflect.Array;
import java.util.Random;

public class SkipList <K extends Comparable<K>> {
    private final SkipListNode<K>[] first;
    private final int DEFAULT_MAX_HEIGHT = 5;
    private int maxHeight;

    public SkipList() {
        first = (SkipListNode<K>[]) Array.newInstance(SkipListNode.class, DEFAULT_MAX_HEIGHT);
        maxHeight = DEFAULT_MAX_HEIGHT;
        for (int i = 0; i < maxHeight; i++) {
            first[i] = new SkipListNode<>(first);
        }
    }

    public SkipListNode<K>[] getFirst() {
        return first;
    }

    public void print() {
        SkipListNode<K> currentSkipListNode = first[0].getNextElement();
        while (currentSkipListNode != null) {
            System.out.print(currentSkipListNode.getKey() + " ; ");
            currentSkipListNode = currentSkipListNode.getNextElement();
        }
    }

    public void insert(K[] keys){
        for (K key : keys) {
            insert(key);
        }
    }

    public void insert(K key) {
        // Prepare insertion
        int numOfLayersNewArray = calcNumLayers() + 1; // The layers start at 0
        SkipListNode<K>[] arrayToInsert = (SkipListNode<K>[]) Array.newInstance(SkipListNode.class, numOfLayersNewArray);

        // Insert the elements of the arrayToInsert into the SkipList
        SkipListNode<K>[] currentBlock = first;
        SkipListNode<K> currentElement;
        SkipListNode<K> nextElement;

        for (int layer = first.length - 1; layer >= 0 ; layer--) {
            currentElement = currentBlock[layer];
            nextElement = currentElement.getNextElement();

            while (nextElement != null && nextElement.getKey().compareTo(key) < 0) {
                // Go one block to the right following the "Express Lane"
                currentElement = nextElement;
                nextElement = nextElement.getNextElement();
                currentBlock = currentElement.getArrayItBelongsTo();
            }
            // If the layer is at the layer height of the last node of arrayToInsert -> Start inserting its nodes
            if (layer <= numOfLayersNewArray - 1) {
                SkipListNode<K> skipListNodeToInsert = new SkipListNode<>(key, nextElement, arrayToInsert);
                arrayToInsert[layer] = skipListNodeToInsert;
                currentBlock[layer].setNextElement(skipListNodeToInsert);
            }
        }
    }

    private int calcNumLayers() {
        Random rand = new Random();
        int randomValue;
        int size = 0;

        do {
            randomValue = rand.nextInt() % 2;
            size++;
        } while (randomValue != 0 && size < maxHeight-1);
        return size;
    }

    public boolean contains(K key) {
        SkipListNode<K>[] currentBlock = first;
        SkipListNode<K> currentSkipListNode;
        SkipListNode<K> nextSkipListNode;

        for (int layer = first.length - 1; layer >= 0; layer--) {
            currentSkipListNode = currentBlock[layer];
            nextSkipListNode = currentSkipListNode.getNextElement();

            while (nextSkipListNode != null && nextSkipListNode.getKey().compareTo(key) < 0) {
                // Go one block to the right following the "Express Lane"
                currentSkipListNode = nextSkipListNode;
                nextSkipListNode = nextSkipListNode.getNextElement();
                currentBlock = currentSkipListNode.getArrayItBelongsTo();
            }
            if (nextSkipListNode != null) {
                if (nextSkipListNode.getKey().compareTo(key) == 0) {
                    // Found key
                    return true;
                }
            }
        }
        return false;
    }

    public void delete(K key) {
        SkipListNode<K>[] currentBlock = first;
        SkipListNode<K> currentSkipListNode;
        SkipListNode<K> nextSkipListNode;

        for (int layer = first.length - 1; layer >= 0; layer--) {
            currentSkipListNode = currentBlock[layer];
            nextSkipListNode = currentSkipListNode.getNextElement();

            while (nextSkipListNode != null && nextSkipListNode.getKey().compareTo(key) < 0) {
                // Go one block to the right following the "Express Lane"
                currentSkipListNode = nextSkipListNode;
                nextSkipListNode = nextSkipListNode.getNextElement();
                currentBlock = currentSkipListNode.getArrayItBelongsTo();
            }
            if (nextSkipListNode != null) {
                if (nextSkipListNode.getKey().compareTo(key) == 0) {
                    // Jump over nodeToDelete
                    SkipListNode<K> skipListNodeToDelete = nextSkipListNode;
                    SkipListNode<K> newNextSkipListNode = skipListNodeToDelete.getNextElement();
                    currentSkipListNode.setNextElement(newNextSkipListNode);
                }
            }
        }
    }
}

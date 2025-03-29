package MyTreeSetTest;

import static org.junit.jupiter.api.Assertions.*;

import MySet.MyTreeSet.SkipList.SkipList;
import MySet.MyTreeSet.SkipList.SkipListNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SkipListTest {
    private SkipList<Integer> list;

    @BeforeEach
    void setup() {
        list = new SkipList<>();
    }

    @Test
    void insert_Sorted() {
        Integer[] keys = {1,2,3,4,5,6,7};
        list.insert(keys);

        SkipListNode<Integer>[] head = list.getFirst();
        SkipListNode<Integer>[] layerZeroElements = new SkipListNode[keys.length];
        SkipListNode<Integer> currentElement = head[0].getNextElement();

        for (int i = 0; i < keys.length; i++) {
            layerZeroElements[i] = currentElement;
            currentElement = currentElement.getNextElement();
        }
        Arrays.sort(keys);
        for (int i = 0; i < keys.length; i++) {
            assertEquals(keys[i],layerZeroElements[i].getKey());
        }
    }

    @Test
    void insert_Random() {
        Integer[] keys = {9,5,8,1,2,10,4};
        list.insert(keys);

        SkipListNode<Integer>[] head = list.getFirst();
        SkipListNode<Integer>[] layerZeroElements = new SkipListNode[keys.length];
        SkipListNode<Integer> currentElement = head[0].getNextElement();

        for (int i = 0; i < keys.length; i++) {
            layerZeroElements[i] = currentElement;
            currentElement = currentElement.getNextElement();
        }
        Arrays.sort(keys);
        for (int i = 0; i < keys.length; i++) {
            assertEquals(keys[i],layerZeroElements[i].getKey());
        }
        list.print();
    }

    @Test
    void contains() {
        Integer[] keys = {1,2,3,4};
        list.insert(keys);

        // First Element
        assertTrue(list.contains(1));

        // Element in the middle
        assertTrue(list.contains(2));

        // Last Element
        assertTrue(list.contains(4));

        // Element the list doesn't contain
        assertFalse(list.contains(10));
    }

    @Test
    void delete() {
        Integer[] keys = {1,2,3,4};
        list.insert(keys);

        // First Element
        list.delete(1);
        assertFalse(list.contains(1));

        // Element in the middle
        list.delete(2);
        assertFalse(list.contains(2));

        // Last Element
        list.delete(4);
        assertFalse(list.contains(4));

        // Element the list doesn't contain
        list.delete(10);

        list.print();
    }
}
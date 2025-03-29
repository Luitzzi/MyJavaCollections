package MyHashSetTest;

import MySet.MyHashSet.MyHashSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>
 * Test class for MyHashSet
 * </p>
 * Dependency: <br>
 * The attribute hashTable of MyHashSet needs to be public!
 */
class MyHashSetTest {
    private MyHashSet<Integer> map;

    private void setup(int sizeHashTable, MyHashSet.HashFunctionType hashFunctionType) {
        map = new MyHashSet<>(Integer.class, hashFunctionType, sizeHashTable);
    }

    // ************************ Test expandHashTable *******************************************************************

    @Test
    public void test_expandHashTable() {
        int sizeHashTable = 10;
        Integer[] keys = {1,2,3,4,5,7,19};

        MyHashSet.HashFunctionType hashFunctionType = MyHashSet.HashFunctionType.linearProbing;
        setup(sizeHashTable, hashFunctionType);
        map.insert(keys);

        Integer[] resultBeforeExpansion = {null,1,2,3,4,5,null,7,null,19};
        assertArrayEquals(resultBeforeExpansion, map.hashTable);

        map.insert(0);
        map.insert(6);

        Integer[] resultAfterExpansion = {0,1,2,3,4,5,19,7,6,null,null,null,null,null,null};
        assertArrayEquals(resultAfterExpansion, map.hashTable);
    }

    // ************************ Test Insert ****************************************************************************
    @Test
    public void test_Exercise_Linear_Probing() {
        int sizeHashTable = 11;
        Integer[] keys = {10,22,31,4,15,28,17,88,59};

        MyHashSet.HashFunctionType hashFunctionType = MyHashSet.HashFunctionType.linearProbing;
        setup(sizeHashTable, hashFunctionType);
        map.insert(keys);

        Integer[] result = {22,88,null,null,4,15,28,17,59,31,10};
        for (int i = 0; i < sizeHashTable; i++) {
            assertEquals(map.hashTable[i], result[i], "Index: " + i);
        }
    }

    @Test
    public void test_Exercise_Quadratic_Probing() {
        int sizeHashTable = 11;
        Integer[] keys = {10,22,31,4,15,28,17,88,59};

        MyHashSet.HashFunctionType hashFunctionType = MyHashSet.HashFunctionType.linearProbing;
        setup(sizeHashTable, hashFunctionType);
        map.insert(keys);

        Integer[] result = {22,88,null,null,4,15,28,17,59,31,10};
        for (int i = 0; i < sizeHashTable; i++) {
            assertEquals(map.hashTable[i], result[i], "Index: " + i);
        }
    }

    //@Test
    public void test_Exercise_Double_Hashing() {
        int sizeHashTable = 11;
        Integer[] keys = {10,22,31,4,15,28,17,88,59};

        MyHashSet.HashFunctionType hashFunctionType = MyHashSet.HashFunctionType.linearProbing;
        setup(sizeHashTable, hashFunctionType);
        map.insert(keys);

        Integer[] result = {22,88,null,null,4,15,28,17,59,31,10};
        for (int i = 0; i < sizeHashTable; i++) {
            assertEquals(map.hashTable[i], result[i], "Index: " + i);
        }
    }

    // ****************************************** Test Contains ********************************************************

    @Test
    public void test_Contains() {
        int sizeHashTable = 10;
        Integer[] keys = {4,14,24};

        MyHashSet.HashFunctionType linearProbing = MyHashSet.HashFunctionType.linearProbing;
        MyHashSet.HashFunctionType quadraticProbing = MyHashSet.HashFunctionType.quadraticProbing;
        MyHashSet.HashFunctionType doubleHashing = MyHashSet.HashFunctionType.doubleHashing;

        setup(sizeHashTable, linearProbing);
        map.insert(keys);

        // First try
        assertTrue(map.contains(4));
        // Multiple trys needed
        assertTrue(map.contains(14));
        assertTrue(map.contains(24));
        // Value not in the hashTable
        assertFalse(map.contains(100));
        // Key used in the probing process for insertion got deleted
        map.delete(4);
        assertTrue(map.contains(14));
    }

    // ******************************** Test Delete ********************************************************************

    @Test
    public void test_Delete() {
        int sizeHashTable = 10;
        Integer[] keys = {4,14,24};

        MyHashSet.HashFunctionType linearProbing = MyHashSet.HashFunctionType.linearProbing;
        MyHashSet.HashFunctionType quadraticProbing = MyHashSet.HashFunctionType.quadraticProbing;
        MyHashSet.HashFunctionType doubleHashing = MyHashSet.HashFunctionType.doubleHashing;

        setup(sizeHashTable, linearProbing);
        map.insert(keys);

        // Delete with zero hops
        map.delete(4);
        assertFalse(map.contains(4));
        // Delete with one hop
        map.delete(14);
        assertFalse(map.contains(14));
        // Delete with two hops
        map.delete(24);
        assertFalse(map.contains(24));
    }
}
package MySet.MyHashSet;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * <p>
 * Implementation of a HashSet with open addressing.
 * </p>
 * <p>
 * Probing variations: <br>
 *      - Linear probing <br>
 *      - Quadratic probing <br>
 *      - Double hashing <br>
 * </p>
 * <p>
 * Default capacity and load factor: <br>
 *      The HashMap has a DEFAULT_CAPACITY of 10, which can be varied at instantiation.
 *      The default load factor is 0.75. The number of inserts is determined by the numOfInserts variable,
 *      if it exceeds the load factor the HashTable gets incremented by the GROWTH_RATE of 1.5 and the keys are rehashed.
 * </p>
 * To be able to recreate the probing steps by the hashFunction a field is marked in the wasFieldUsed array
 * to determine if the value doesn't exist in the hashTable or further probing is necessary. This is important in the
 * methods contains and delete.
 * @param <K> the type of keys in this map.
 */
public class MyHashSet<K extends Number & Comparable<K>> {
    public K[] hashTable; // Public is necessary for the tests
    private final Class<K> keyType;
    private final int DEFAULT_CAPACITY = 19;
    private final double LOAD_FACTOR = 0.75F;
    private final float GROWTH_RATE = 1.5F;

    private int numOfInserts;
    private boolean[] wasFieldUsed;

    public enum HashFunctionType {linearProbing, quadraticProbing, doubleHashing}
    private HashFunctionType hashFunctionType;

    public MyHashSet(Class<K> clazz, HashFunctionType hashFunctionType) {
        keyType = clazz;
        hashTable = (K[]) Array.newInstance(clazz, DEFAULT_CAPACITY);
        wasFieldUsed = new boolean[DEFAULT_CAPACITY]; // Default value is false
        this.hashFunctionType = hashFunctionType;
    }

    public MyHashSet(Class<K> clazz, HashFunctionType hashFunctionType, int initialCapacity) {
        if (initialCapacity >= 0) {
            keyType = clazz;
            hashTable = (K[]) Array.newInstance(clazz, initialCapacity);
            wasFieldUsed = new boolean[initialCapacity]; // Default value is false
            this.hashFunctionType = hashFunctionType;
        }
        else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public void printHashtable() {
        for (int i = 0; i < hashTable.length; i++) {
            System.out.print(hashTable[i] + ";");
        }
        System.out.print("\n");
    }

    public void insert(K[] keys) {
        for (K key : keys) {
            insert(key);
        }
    }

    public void insert(K key) {
        int numOfTrys = 0;
        insert(key, numOfTrys);
    }

    private void insert(K key, int numOfTrys) {
        int capacityRatio = (int) (hashTable.length * LOAD_FACTOR);
        // Check if hashTable is too full and needs to be expanded
        if (numOfInserts <= capacityRatio) {
            int position = getPosition(key, numOfTrys, hashTable.length);
            if (hashTable[position] == null && !wasFieldUsed[position]) {
                // Base case: Position is empty and wasn't used before
                hashTable[position] = key;
                numOfInserts++;
            } else {
                insert(key, ++numOfTrys);
            }
        }
        else {
            expandHashTable();
            insert(key, numOfTrys);
        }
    }

    /**
     * Used to rehash the keys from the old hashTable into the new one.
     * @param key
     * @param numOfTrys
     * @param newHashTable The expanded hashTable into that the keys from the old one need to be inserted.
     */
    private void insert(K key, int numOfTrys, K[] newHashTable) {
        int position = getPosition(key, numOfTrys, newHashTable.length);
        if (newHashTable[position] == null) {
            // Base case: Position is empty
            newHashTable[position] = key;
        } else {
            insert(key, ++numOfTrys, newHashTable);
        }
    }

    public boolean contains(K key) {
        int numOfTrys = 0;
        int startPosition = getPosition(key,numOfTrys,hashTable.length);
        if (Objects.equals(hashTable[startPosition], key)) {
            System.out.println("Key " + key + " is in the hashTable at position: " + startPosition + ".");
            return true;
        }
        else if(hashTable[startPosition] == null
                && !wasFieldUsed[startPosition]) {
            System.out.println("Key " + key + " is not in the hashTable.");
            return false;
        }
        else {
            return contains(key, ++numOfTrys, startPosition);
        }
    }

    private boolean contains(K key, int numOfTrys, int startPosition) {
        int position = getPosition(key, numOfTrys, hashTable.length);
        if (Objects.equals(hashTable[position], key)) {
            System.out.println("Key " + key + " is in the hashTable at position: " + position + ".");
            return true;
        }
        else if(position == startPosition || hashTable[position] == null
                && !wasFieldUsed[position]) {
            System.out.println("Key " + key + " is not in the hashTable.");
            return false;
        }
        else {
            return contains(key, ++numOfTrys, startPosition);
        }
    }

    public boolean delete(K key) {
        int numOfTrys = 0;
        return delete(key, numOfTrys);
    }

    private boolean delete(K key, int numOfTrys) {
        int position = getPosition(key,numOfTrys,hashTable.length);
        if (Objects.equals(hashTable[position], key)) {
            hashTable[position] = null;
            wasFieldUsed[position] = true;
            return true;
        }
        else if(hashTable[position] == null
                && !wasFieldUsed[position]) {
            System.out.println("Key " + key + " is not in the hashTable.");
            return false;
        }
        else {
            return delete(key, ++numOfTrys);
        }
    }

    /**
     * Get the position of a new key in the hashMap using the predefined hashFunctionType
     * @param key
     * @param numOfTrys Tracks to number of trys already made to insert the element.
     *                  Needed to calculate the position according to the hashFunctionType.
     * @param hashTableSize Necessary to distinguish between an insert into the hashTable, or while rehashing
     *                      for the expandHashTable method into the newHashTable.
     * @return position of the key.
     */
    private int getPosition(K key, int numOfTrys, int hashTableSize) {
        return switch (hashFunctionType) {
            case linearProbing -> hashLinearProbing(key, numOfTrys, hashTableSize);
            case quadraticProbing -> hashQuadraticProbing(key, numOfTrys, hashTableSize);
            case doubleHashing -> doubleHashing(key, numOfTrys, hashTableSize);
        };
    }

    // h(key) = (key + numOfTrys) % hashTableSize
    private int hashLinearProbing(K key, int numOfTrys, int hashTableSize) {
        return (key.intValue() + numOfTrys) % hashTableSize;
    }

    // h(key) = ( key + 1 * numOfTrys + 3 * numOfTrys²) % hashTableSize
    private int hashQuadraticProbing(K key, int numOfTrys, int hashTableSize) {
        int firstCoeffizient = 1;
        int secondCoeffizient = 3;
        int polynom1Degree = numOfTrys;
        int polynom2Degree = secondCoeffizient * numOfTrys * numOfTrys;
        return (key.intValue() + polynom1Degree + polynom2Degree) % hashTableSize;
    }

    // h(key) = (key + numOfTrys * (1 + key %(hastTableSize - 1)) % hashTableSize
    private int doubleHashing(K key, int numOfTrys, int hashTableSize) {
        int secondModuloValue = hashTableSize - 1;
        int secondHashingValue = 1 + key.intValue() % secondModuloValue;
        return (key.intValue() + numOfTrys * secondHashingValue) % hashTableSize;
    }

    private void expandHashTable() {
        int newArrayLength = (int) (hashTable.length * GROWTH_RATE);
        K[] newHashTable = (K[]) Array.newInstance(keyType, newArrayLength);
        boolean[] newWasFieldUsed = new boolean[newArrayLength]; // Default value is false
        rehashKeys(newHashTable);
        this.hashTable = newHashTable;
        this.wasFieldUsed = newWasFieldUsed;
    }

    /**
     * Rehash all keys from the old hashTable into the new one.
     * @param newHashTable new hashTable with the exceeded length.
     */
    private void rehashKeys(K[] newHashTable) {
        for (K key : hashTable) {
            if (key != null) {
                insert(key, 0, newHashTable);
            }
        }
    }
}

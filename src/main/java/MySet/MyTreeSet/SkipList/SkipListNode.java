package MySet.MyTreeSet.SkipList;

public class SkipListNode<K extends Comparable<K>>{
    private K key;
    private SkipListNode<K> nextElement;
    private SkipListNode<K>[] belongsTo;

    public SkipListNode(K key) {
        this.key = key;
    }

    public SkipListNode(SkipListNode<K> nextElement) {
        this.nextElement = nextElement;
    }

    public SkipListNode(SkipListNode<K>[] belongsTo) {
        this.belongsTo = belongsTo;
    }

    public SkipListNode(K key, SkipListNode<K> nextElement) {
        this.key = key;
        this.nextElement = nextElement;
    }

    public SkipListNode(K key, SkipListNode<K> nextElement, SkipListNode<K>[] belongsTo) {
        this.key = key;
        this.nextElement = nextElement;
        this.belongsTo = belongsTo;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public SkipListNode<K> getNextElement() {
        return nextElement;
    }

    public void setNextElement(SkipListNode<K> nextElement) {
        this.nextElement = nextElement;
    }

    public SkipListNode<K>[] getArrayItBelongsTo() {
        return belongsTo;
    }

    public void setArrayItBolongsTo(SkipListNode<K>[] belongsTo) {
        this.belongsTo = belongsTo;
    }

    public void fillNode(K key, SkipListNode<K> nextElement, SkipListNode<K>[] belongsTo, int index) {
        belongsTo[index].setKey(key);
        belongsTo[index].setNextElement(nextElement);
        belongsTo[index].setArrayItBolongsTo(belongsTo);
    }
}

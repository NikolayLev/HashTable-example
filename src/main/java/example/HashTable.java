package example;

public class HashTable<K, V> {
    private K node;
    private V value;
    private Node<K, V>[] innerArray;
    private int innerArrayLength;

    public void put(K key, V value) {
        Node<K, V> node = new Node<K, V>(key, value);
        int innerPlace = findPlaceInInnerArray(innerArrayLength, key.hashCode());
        boolean addIsComplete = false;


        if (innerArray[innerPlace] == null) {
            innerArray[innerPlace] = node;
            addIsComplete = true;
        } else {
            Node<K, V> innerNode = innerArray[innerPlace];
            while (!addIsComplete) {

                if (innerNode.hash == key.hashCode() && innerNode.key.equals(key)) {
                    innerNode.value = value;
                    addIsComplete = true;
                } else {
                    if (innerNode.nextNode == null) {
                        innerNode.nextNode = node;
                        addIsComplete = true;
                    } else {
                        innerNode = innerNode.nextNode;
                    }
                }

            }
        }

    }

    //вспомогательный метод для поиска места в иннер массиве
    private int findPlaceInInnerArray(int innerArrayLength, int hash) {
        int innerPlace;
        innerPlace = hash % (innerArrayLength - 1);
        return innerPlace;
    }

    public V get(K key){
    return findNode(key).value;
    }

    public boolean delete() {
        return false;
    }

    private Node<K, V> findNode(K key) {
        int innerPlace = findPlaceInInnerArray(innerArrayLength, key.hashCode());
        if (innerArray[innerPlace] == null) {
            return null;
        }
        Node<K, V> innerNode = innerArray[innerPlace];
        boolean findIsComplete = false;

        while (!findIsComplete) {

            if (innerNode.hash == key.hashCode() && innerNode.key.equals(key)) {
                return innerNode;
            } else {
                if (innerNode.nextNode == null) {
                    return null;
                } else {
                    innerNode = innerNode.nextNode;
                }
            }
        }
        return null;
    }

    public boolean contains(K key) {
        return false;
    }

    public HashTable() {
        innerArray = new Node[16];
        innerArrayLength = innerArray.length;
    }

    private class Node<K, V> {
        K key;
        V value;
        int hash;
        Node<K, V> nextNode;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
        }

    }

}

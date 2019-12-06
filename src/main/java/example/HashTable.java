package example;

import java.util.Arrays;

public class HashTable<K, V> {
    private K node;
    private V value;
    private Node<K, V>[] innerArray;
    private int innerArrayLength;
    private float loadFactory = 0.5f;
    private int size = 0;

    public int getSize() {
        return size;
    }

    //количество занятых ячеек в массиве
    private float load = 0f;

    public void put(K key, V value) {
        if (load / innerArrayLength >= 0.75f) {
            expandInnerArray();
        }
        put(key, value, innerArray);
    }

    private void put(K key, V value, Node[] nodes) {
        Node<K, V> node = new Node<K, V>(key, value);
        int innerPlace = findPlaceInInnerArray(innerArrayLength, key.hashCode());
        boolean addIsComplete = false;

        if (nodes[innerPlace] == null) {
            nodes[innerPlace] = node;
            size++;
            load++;
            addIsComplete = true;

        } else {
            Node<K, V> innerNode = nodes[innerPlace];
            while (!addIsComplete) {

                if (innerNode.hash == key.hashCode() && innerNode.key.equals(key)) {
                    innerNode.value = value;
                    addIsComplete = true;

                } else {
                    if (innerNode.nextNode == null) {
                        innerNode.nextNode = node;
                        node.previousNode = innerNode;
                        size++;
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
        innerPlace = (innerArrayLength - 1) & hash;
        System.out.println(innerPlace);
        return innerPlace;
    }

    public V get(K key) {
        return findNode(key).value;
    }

    public boolean delete(K key) {
        Node<K, V> deletedNode = findNode(key);
        if (deletedNode == null) {
            return false;
        }
        if (deletedNode.previousNode == null && deletedNode.nextNode == null) {
            innerArray[findPlaceInInnerArray(innerArrayLength, deletedNode.key.hashCode())] = null;
            size--;
            return true;

        }

        if (deletedNode.previousNode != null) {
            deletedNode.previousNode.nextNode = deletedNode.nextNode;
        }
        if (deletedNode.nextNode != null) {
            deletedNode.nextNode.previousNode = deletedNode.previousNode;
        }
        size--;
        return true;
    }

    //вынес поиск в отдельный метод
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
        if (findNode(key) != null) {
            return true;
        } else return false;
    }

    public HashTable() {
        innerArray = new Node[8];
        innerArrayLength = innerArray.length;
    }

    private void expandInnerArray() {
        Node<K, V> newInnerArray[] = new Node[innerArrayLength * 2];
        Entry<K, V>[] entrySet = getEntrySet();
        System.out.println(Arrays.toString(entrySet));
        innerArrayLength = innerArrayLength * 2;
        load = 0;
        size = 0;

        for (Entry<K, V> entry : entrySet) {
            put(entry.key, entry.value, newInnerArray);
            System.out.println("put");
        }

        innerArray = newInnerArray;
        System.out.println("Пересобрали внутринний массив");

    }

    public Entry<K, V>[] getEntrySet() {
        Entry<K, V>[] entrySet = new Entry[size];
        int nextSlot = 0;
        for (int i = 0; i < innerArrayLength; i++) {
            if (innerArray[i] != null) {
                Node<K, V> innerNode = innerArray[i];
                entrySet[nextSlot++] = new Entry<K, V>(innerNode.key, innerNode.value);
                boolean hasNext = false;
                while (innerNode.nextNode != null) {
                    innerNode = innerNode.nextNode;
                    entrySet[nextSlot++] = new Entry<K, V>(innerNode.key, innerNode.value);
                }
            }
        }
        return entrySet;
    }


    public class Entry<K, V> {
        private K key;
        private V value;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public String toString() {
            return key + " : " + value;
        }

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class Node<K, V> {
        K key;
        V value;
        int hash;
        Node<K, V> nextNode;
        Node<K, V> previousNode;

        public int hashCode() {
            return key.hashCode();
        }

        public boolean equals(Object o) {
            return this.key.equals(o);
        }

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = key.hashCode();
        }

    }

}

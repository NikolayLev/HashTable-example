package example;

public class HashTable<K, V> {
    private K node;
    private V value;
    private Node<K, V>[] bucket;
    private int bucketLength;
    private final float LOAD_FACTORY = 0.75f;
    private int size = 0;

    public int getSize() {
        return size;
    }

    //количество занятых ячеек в массиве
    private float load = 0f;


    public void put(K key, V value) {
        if (load / bucketLength >= LOAD_FACTORY) {
            expandInnerArray();
        }
        Node<K, V> node = new Node<>(key, value);
        int innerPlace = findPlaceInInnerArray(bucketLength, key.hashCode());
        boolean addIsComplete = false;

        if (bucket[innerPlace] == null) {
            bucket[innerPlace] = node;
            size++;
            load++;
            addIsComplete = true;

        } else {
            Node<K, V> innerNode = bucket[innerPlace];
            while (!addIsComplete) {

                if (innerNode.hash == key.hashCode() && innerNode.key.equals(key)) {
                    innerNode.value = value;
                    addIsComplete = true;

                } else if (innerNode.nextNode == null) {
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


    //вспомогательный метод для поиска места в иннер массиве
    private int findPlaceInInnerArray(int innerArrayLength, int hash) {
        return (innerArrayLength - 1) & hash;
    }

    public V get(K key) {
        V node = findNode(key).value;
        return node != null ? node : null;
    }

    public boolean delete(K key) {
        Node<K, V> deletedNode = findNode(key);
        if (deletedNode == null) {
            return false;
        }
        if (deletedNode.previousNode == null && deletedNode.nextNode == null) {
            bucket[findPlaceInInnerArray(bucketLength, deletedNode.key.hashCode())] = null;
            size--;
            load--;
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
        int innerPlace = findPlaceInInnerArray(bucketLength, key.hashCode());
        if (bucket[innerPlace] == null) {
            return null;
        }
        Node<K, V> innerNode = bucket[innerPlace];
        boolean findIsComplete = false;


        while (!findIsComplete) {

            if (innerNode.hash == key.hashCode() && innerNode.key.equals(key)) {
                return innerNode;
            } else if (innerNode.nextNode == null) {
                return null;
            } else {
                innerNode = innerNode.nextNode;
            }
        }
        return null;
    }


    public boolean contains(K key) {
        if (findNode(key) != null) {
            return true;
        } else {
            return false;
        }
    }

    public HashTable() {
        bucket = new Node[16];
        bucketLength = bucket.length;
    }

    private void expandInnerArray() {
        Entry<K, V>[] entrySet = getEntrySet();
        bucket = new Node[bucketLength * 2];

        bucketLength = bucketLength * 2;
        load = 0;
        size = 0;

        for (Entry<K, V> entry : entrySet) {
            put(entry.key, entry.value);
        }

    }

    public Entry<K, V>[] getEntrySet() {
        Entry<K, V>[] entrySet = new Entry[size];
        int nextSlot = 0;
        for (int i = 0; i < bucketLength; i++) {
            if (bucket[i] == null) {
                continue;
            }
            Node<K, V> innerNode = bucket[i];
            entrySet[nextSlot++] = new Entry<K, V>(innerNode.key, innerNode.value);
            boolean hasNext = false;
            while (innerNode.nextNode != null) {
                innerNode = innerNode.nextNode;
                entrySet[nextSlot++] = new Entry<K, V>(innerNode.key, innerNode.value);
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

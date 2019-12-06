package example;

import java.util.Arrays;

public class app {
    public static void main(String[] args) {

        HashTable<String, Integer> hashTable = new HashTable<>();
        hashTable.put("1", 123);
        hashTable.put("2", 1243);
        hashTable.put("3", 123);
        hashTable.put("4", 123);
        hashTable.put("5", 123);
        hashTable.put("6", 123);
        hashTable.put("7", 1243);
        hashTable.put("8", 123);
        hashTable.put("9", 123);

        System.out.println();


        System.out.println(Arrays.toString(hashTable.getEntrySet()));

        hashTable.delete("2");
        System.out.println(Arrays.toString(hashTable.getEntrySet()));

        System.out.println(hashTable.contains("2"));
        System.out.println(hashTable.contains("3"));
        System.out.println(hashTable.get("1"));


    }

}

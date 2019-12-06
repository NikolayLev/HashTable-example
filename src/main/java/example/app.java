package example;

import java.util.Arrays;

public class app {
    public static void main(String[] args) {

        HashTable<String,Integer> hashTable = new HashTable<>();
        hashTable.put("Assdasd",123);
        hashTable.put("Assda",1243);
        hashTable.put("Asssd",123);
        hashTable.put("As123sd",123);
        hashTable.put("Assaasd",123);



        System.out.println(Arrays.toString(hashTable.getEntrySet()));
    }

}

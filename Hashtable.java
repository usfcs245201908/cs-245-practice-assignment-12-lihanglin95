import java.util.ArrayList;

import static java.lang.Math.abs;

public class Hashtable {
    private ArrayList<HashNode> bucket;
    private int numBuckets;
    private double LOAD_THRESHOLD;
    private int entries;

    /**
     * constructor
     */
    public Hashtable(){
        numBuckets = 10000;
        bucket = new ArrayList<HashNode>();
        LOAD_THRESHOLD = 0.5;
        entries = 0;

        for(int i = 0; i < numBuckets; i++){
            bucket.add(null);
        }
    }

    /**
     * This is the inner class HashNode
     */
    class HashNode{
        String key;
        String value;
        HashNode next;

        public HashNode(String key, String value){
            this.key = key;
            this.value = value;
            next = null;
        }

    }

    /**
     * This method is to get the Hash Code of the key
     * @param key This is the key
     * @return This returns the Hash Code
     */
    int getHash(String key){
        return abs(key.hashCode() % numBuckets);
    }

    /**
     * This method is to check if the key exists
     * @param key This is the key
     * @return This returns <code>true</code> if the key exists otherwise returns <code>false</code>
     */
    public boolean containsKey(String key){
        return bucket.get(getHash(key)) != null;
    }

    /**
     * This method is to find the the key/value pair in the hash table
     * @param key This is the key
     * @return This returns the value of the key returns null if the key does not exist
     */
    public String get(String key){
        HashNode head = bucket.get(getHash(key));

        while(head != null){
            if(head.key == key){
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * This method is to add the key/value pair into the hash table
     * replace the value if the key exists
     * @param key This is the key
     * @param value This is the value
     */
    public void put(String key, String value){
        int hashCode = getHash(key);
        HashNode head = bucket.get(hashCode);

        if(head == null){
            bucket.set(hashCode, new HashNode(key, value));
        }else{
            while(head != null){
                if(head.key == key){
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            HashNode node = new HashNode(key, value);
            node.next = bucket.get(hashCode);
            bucket.set(hashCode, node);
        }
        entries++;
        if((entries * 1.0 / numBuckets) >= LOAD_THRESHOLD){
            increaseBucket();
        }
    }

    /**
     * This method is to remove the key/value pair from the hash table
     * @param key This is the key
     * @return This returns the value of the key
     * @throws Exception If the key is not present in the hash table
     */
    public String remove(String key) throws Exception {
        int hashCode = getHash(key);
        HashNode head = bucket.get(hashCode);

        if(head != null){
            if(head.key == key){
                bucket.set(hashCode, head.next);
                entries--;
                return head.value;
            }else{
                HashNode prev = head;
                HashNode current = null;
                while(prev.next != null){
                    current = prev.next;
                    if(current.key == key){
                        prev.next = current.next;
                        entries--;
                        return current.value;
                    }
                }
                return head.value;
            }
        }else{
            throw new Exception();
        }
    }

    /**
     * This method is to increase the number of buckets by 2 if the <code>LOAD_THRESHOLD</code> is greater than 0.5
     */
    void increaseBucket() {
        ArrayList<HashNode> temp = bucket;
        numBuckets *= 2;
        bucket = new ArrayList<>(numBuckets);

        for(int i = 0; i < numBuckets; i++){
            bucket.add(null);
        }

        for(HashNode head : temp){
            while(head != null){
                put(head.key, head.value);
                head = head.next;
            }
        }

    }


}

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Hashtable {
    private ArrayList<HashNode> bucket;
    private int numBuckets;
    private double LOAD_THRESHOLD;
    private int entries;

    public Hashtable(){
        numBuckets = 10000;
        bucket = new ArrayList<HashNode>();
        LOAD_THRESHOLD = 0.5;
        entries = 0;

        for(int i = 0; i < numBuckets; i++){
            bucket.add(null);
        }
    }

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

    int getHash(String key){
        return abs(key.hashCode() % numBuckets);
    }

    public boolean containsKey(String key){
        return bucket.get(getHash(key)) != null;
    }

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

    public void put(String key, String value){
        HashNode head = bucket.get(getHash(key));

        if(head == null){
            bucket.set(getHash(key), new HashNode(key, value));
        }else{
            while(head != null){
                if(head.key == key){
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            HashNode node = new HashNode(key, value);
            node.next = bucket.get(getHash(key));
            bucket.set(getHash(key), node);
        }
        entries++;
        if((entries * 1.0 / numBuckets) >= LOAD_THRESHOLD){
            increaseBucket();
        }
    }

    public String remove(String key) throws Exception {
        HashNode head = bucket.get(getHash(key));

        if(head != null){
            if(head.key == key){
                bucket.set(getHash(key), head.next);
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

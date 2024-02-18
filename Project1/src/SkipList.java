import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class SkipList<K extends Comparable<? super K>, V> implements Iterable<KVPair<K, V>> {
    private SkipNode head; // First element (Sentinel Node)
    private int size; // number of entries in the Skip List
    private int level; //Highest level of the skiplist

    /**
     * Initializes the fields head, size and level
     */
    public SkipList() {
        head = new SkipNode(null, 0);
        size = 0;
        level = 0;
    }


    /**
     * Returns a random level number which is used as the depth of the SkipNode
     * 
     * @return a random level number
     */
    int randomLevel() {
        int lev;
        Random value = new Random();
        for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
            // Do nothing
        }
        return lev; // returns a random level
    }


    /**
     * Searches for the KVPair using the key which is a Comparable object.
     * 
     * @param key
     *            key to be searched for
     * @return ArrayList<KVPair<K, V>>
     *            List of every rectangle with that name
     *        
     */
    public ArrayList<KVPair<K, V>> search(K key) {
        ArrayList<KVPair<K, V>> resultList = new ArrayList<>();
        SkipNode x = head;
        for (int i = level; i >= 0; i--) {
        while ((x.forward[i] != null) && (x.forward[i].pair.getKey().compareTo (key) < 0)) {
            x = x.forward[i];
            }
        }
        x = x.forward[0];
        while (x != null && x.pair.getKey().compareTo(key) == 0) {
            resultList.add(x.pair);
            x = x.forward[0];
        }
        return resultList;
    }
    


    /**
     * @return the size of the SkipList
     */
    public int size() {
        return size;
    }


    /**
     * Inserts the KVPair in the SkipList at its appropriate spot as designated
     * by its lexicoragraphical order.
     * 
     * @param it
     *            the KVPair to be inserted
     */
    @SuppressWarnings("unchecked")
    public void insert(KVPair<K, V> it) {
    	int level = randomLevel();
        SkipNode newNode = new SkipNode(it, level);

        if (level > head.level) {
            adjustHead(level);
        }

       
        SkipNode[] update = ((SkipNode[])Array.newInstance(SkipList.SkipNode.class,  level +1));
        SkipNode current = head;

        for (int i = 0; i <= level; i++) {
            update[i] = head;
        }

        for (int i = this.level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].pair.getKey().compareTo(it.getKey()) < 0) {
                current = current.forward[i];
            }
            if (i <= level) {
                update[i] = current;
            }
        }

        //Update pointers
        for (int i = 0; i <= level; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }

        size++;
    }


    /**
     * Increases the number of levels in head so that no element has more
     * indices than the head.
     * 
     * @param newLevel
     *            the number of levels to be added to head
     */
    @SuppressWarnings("unchecked")
    public void adjustHead(int newLevel) {
    	SkipNode[] newForward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,  newLevel + 1);
        System.arraycopy(head.forward,  0,  newForward,  0,  head.forward.length);
        head.forward = newForward;
        head.level = newLevel;
        this.level = newLevel;
    }


    /**
     * Removes the KVPair that is passed in as a parameter and returns true if
     * the pair was valid and false if not.
     * 
     * @param key
     *            the KVPair to be removed
     * @return KVPair<K, V>
     *          returns the removed pair if the pair was valid and null if not
     */
    @SuppressWarnings("unchecked")
    public KVPair<K, V> remove(K key) {
        SkipNode current = head;
        SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, head.level + 1);

        //Finding the Node
        for (int i = head.level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].element().getKey().compareTo(key) <0) {
                current = current.forward[i];
            }
            update [i] = current;
        }
        current = current.forward[0];

        if (current != null && current.element().getKey().equals(key)) {
            for (int i = 0; i <= head.level && update[i].forward[i] == current; i++) {
                update[i].forward[i] = current.forward[i];

            }
            while (head.level > 0 && head.forward[head.level]== null) {
                head.level--;
            }
            return current.element();
        }
        return null;
    }
  
    /**
     * Removes a KVPair with the specified value.
     * 
     * @param val
     *            the value of the KVPair to be removed
     * @return returns true if the removal was successful
     */
    public KVPair<K, V> removeByValue(V val) {
        SkipNode current = head;
        SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, head.level + 1);

        for (int i = head.level; i >= 0; i--) {
            while (current.forward[i] != null && !current.forward[i].element().getValue().equals(val)) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current != null && current.element().getValue().equals(val)) {
            for (int i = 0; i <= head.level && update[i].forward[i] == current; i++) {
                update[i].forward[i] = current.forward[i];

            }
            while (head.level > 0 && head.forward[head.level]== null) {
                head.level--;
            }
            return current.element();
        }
        return null;

    }

    /**
     * Prints out the SkipList in a human readable format to the console.
     */
    public void dump() {
    	System.out.println("SkipList dump:");
        SkipNode current = head.forward[0];
        int index = 0;
        while (current != null) {
            System.out.println("node " + index + ": " + current.pair.toString() + " Level: " + current.level);
            current = current.forward[0];
            index++;
        }
        System.out.println("SkipList size is: " + size);
    }

    /**
     * This class implements a SkipNode for the SkipList data structure.
     * 
     * @author CS Staff
     * 
     * @version 2016-01-30
     */
    private class SkipNode {

        // the KVPair to hold
        private KVPair<K, V> pair;
        // An array of pointers to subsequent nodes
        private SkipNode[] forward;
        // the level of the node
        private int level;

        /**
         * Initializes the fields with the required KVPair and the number of
         * levels from the random level method in the SkipList.
         * 
         * @param tempPair
         *            the KVPair to be inserted
         * @param level
         *            the number of levels that the SkipNode should have
         */
        @SuppressWarnings("unchecked")
        public SkipNode(KVPair<K, V> tempPair, int level) {
            pair = tempPair;
            forward = (SkipNode[])Array.newInstance(SkipList.SkipNode.class,
                level + 1);
            this.level = level;
        }


        /**
         * Returns the KVPair stored in the SkipList.
         * 
         * @return the KVPair
         */
        public KVPair<K, V> element() {
            return pair;
        }

    }
    
    private class SkipListIterator implements Iterator<KVPair<K, V>> {
        private SkipNode current;
		
        public SkipListIterator() {
        	current = head;
        }
        @Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current.forward[0] != null;
		}

		@Override
		public KVPair<K, V> next() {
			// TODO Auto-generated method stub
			KVPair<K, V> elem = current.forward[0].element();
			current = current.forward[0];
			return elem;
		}
    	
    }

	@Override
	public Iterator<KVPair<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new SkipListIterator();
	}

}

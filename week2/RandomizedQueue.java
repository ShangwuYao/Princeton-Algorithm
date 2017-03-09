import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.*;

/**
 * Created by Administrator on 2017/2/5.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int N;
    private int capacity;
    private Item[] queue;
    public RandomizedQueue(){
        N = 0;
        capacity = 8;
        queue = (Item[]) new Object[capacity];
    }

    public boolean isEmpty(){
        return N==0;
    }

    public int size(){
        return N;
    }

    public void enqueue(Item item){
        if (item == null) {throw new NullPointerException();}
        if (N >= capacity - 1){
            resizePlus();
        }
        queue[N++] = item;
    }

    private void resizePlus(){
        capacity *= 2;
        Item[] newqueue = (Item[]) new Object[capacity];
        for (int i =0; i<N;i++){
            newqueue[i] = queue[i];
        }
        queue = newqueue;
    }
    private void resizeMinus(){
        capacity /= 2;
        Item[] newqueue = (Item[]) new Object[capacity];
        for (int i =0; i<N;i++){
            newqueue[i] = queue[i];
        }
        queue = newqueue;
    }

    public Item dequeue(){
        if (N == 0) {throw new NoSuchElementException();}
        int i = StdRandom.uniform(N);
        Item temp = queue[i];
        queue[i] = queue[--N];
        queue[N] = null;
        if (N<capacity/4){
            resizeMinus();
        }
        return temp;
    }

    public Item sample(){
        if (N == 0) {throw new NoSuchElementException();}
        int i = StdRandom.uniform(N);
        Item temp = queue[i];
        return temp;
    }

    @Override
    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private int current = 0;
        private int[] shuffleindexes = new int[N];
        public boolean hasNext(){
            return current < N;
        }
        public Item next(){
            if (!hasNext()){throw new NoSuchElementException();}
            if (current == 0){
                for (int i = 0;i<N;i++){
                    shuffleindexes[i] = i;
                }
                StdRandom.shuffle(shuffleindexes);
            }
            return queue[shuffleindexes[current++]];
        }
        public void Remove(){throw new UnsupportedOperationException();}
    }
    public static void main(String[] args){
        RandomizedQueue<String> rd = new RandomizedQueue<>();
        rd.enqueue("A");
        rd.enqueue("B");
        rd.enqueue("C");
        rd.enqueue("D");
        rd.enqueue("E");
        rd.enqueue("F");
        for (String s : rd){
            System.out.println(s);
        }
        System.out.println("*************");
        System.out.println(rd.dequeue());
        System.out.println(rd.dequeue());
        System.out.println(rd.dequeue());
        System.out.println(rd.dequeue());
        System.out.println(rd.dequeue());
        System.out.println(rd.dequeue());
        System.out.println(rd.dequeue());
    }
}

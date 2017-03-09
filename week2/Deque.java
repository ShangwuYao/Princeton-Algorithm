import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.*;

/**
 * Created by Administrator on 2017/2/5.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node end;
    private int size = 0;
    public Deque(){
        //construct an empty deque
        front = null;
        end = null;
        size = 0;
    }
    private class Node{
        Node Prev;
        Node Next;
        Item item;
    }
    //is empty?
    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }
    public void addFirst(Item item){
        if (item == null){throw new NullPointerException();}
        Node oldfront = front;
        front = new Node();
        front.Prev = null;
        front.item = item;
        front.Next = oldfront;
        if (oldfront != null){oldfront.Prev = front;}
            else {end = front;}
        size++;
    }

    public void addLast(Item item){
        if (item == null){throw new NullPointerException();}
        Node oldlast = end;
        end = new Node();
        end.Prev = oldlast;
        end.item = item;
        end.Next = null;
        if (oldlast != null){oldlast.Next = end;}
            else {front = end;}
        size++;
    }

    public Item removeFirst(){
        if (size == 0) {throw new NoSuchElementException();}
        Node oldfront = front;
        front = front.Next;
        /**
        front.Prev = null;
        oldfront.Prev=null;
        oldfront.Next=null;
        oldfront.item = null;
        oldfront = null;
         */
        Item temp = oldfront.item;

        if (size == 1) {front = null; end = null;}
        size--;
        return temp;
    }

    public Item removeLast(){
        if (size == 0) {throw new NoSuchElementException();}

        Node oldlast = end;
        end = end.Prev;
        /**
        end.Next = null;
        oldlast.Prev=null;
        oldlast.Next=null;
        oldlast.item = null;
        oldlast = null;
         */
        Item temp = oldlast.item;
        if (size == 1) {front = null; end = null;}

        size--;
        return temp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node Current = front;
        public boolean hasNext(){
            return Current!=null;
        }
        public Item next(){
            if (!hasNext()){throw new NoSuchElementException();}
            Item item = Current.item;
            Current = Current.Next;
            return item;
        }
        public void Remove(){throw new UnsupportedOperationException();}
    }

    public static void main(String[] args){
        try {
            Deque<Integer> sd = new Deque<>();
            sd.addLast(0);
            sd.removeFirst();
            StdOut.println(sd.isEmpty());
            StdOut.println(sd.isEmpty());
            sd.addLast(4);
            sd.removeFirst();
            for (int a : sd) {
                System.out.println(a);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }

}

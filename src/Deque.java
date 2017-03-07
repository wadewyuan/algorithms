import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by wadey on 3/6/2017.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private int size;

    public Deque() {
        first  = null;
        size = 0;
    } // construct an empty deque
    public boolean isEmpty() {
        return first == null;
    } // is the deque empty?
    public int size() {
        return size;
    } // return the number of items on the deque
    public void addFirst(Item item) {
        if(item == null) throw new NullPointerException("Item is null");
        if(isEmpty()) {
            first = new Node<Item>(item);
        } else {
            Node<Item> newFirst = new Node<Item>(item);
            newFirst.next = first;
            first = newFirst;
        }
        size++;
    } // add the item to the front
    public void addLast(Item item) {
        if(item == null) throw new NullPointerException("Item is null");
        if(isEmpty()) {
            first = new Node(item);
        } else {
            Node last = getLast();
            Node newLast = new Node(item);
            last.next = newLast;
        }
        size++;
    } // add the item to the end
    public Item removeFirst() {
        if(isEmpty()) throw new NoSuchElementException("Removing from an empty deque");
        Node<Item> tmpFirst = first;
        first = tmpFirst.next;
        tmpFirst.next = null;
        size--;
        return tmpFirst.item;
    } // remove and return the item from the front
    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException("Removing from an empty deque");
        Item lastItem;
        if(first.next == null) { // remove the first node when there is only one node in queue
            lastItem = first.item;
            first = null;
            size--;
            return lastItem;
        }
        Node<Item> tmp = first;
        while (tmp.next.next != null) { // get the second last node
            tmp = tmp.next;
        }
        lastItem = (Item) tmp.next.item;
        tmp.next = null;
        size--;
        return lastItem;
    } // remove and return the item from the end
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>();
    } // return an iterator over items in order from front to end
    private Node<Item> getLast() {
        Node<Item> last = first;
        while (last.next != null) {
            last = last.next;
        }
        return last;
    }
    private class DequeIterator<Item> implements Iterator<Item> {
        Node<Item> current = (Node<Item>) first;
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException("No more elements");
            Item item = current.item;
            current = current.next;
            return item;
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removing from iterator is not supported.");
        }
    }
    private static class Node<Item> {
        Item item;
        Node next;
        public Node(Item item) {
            this.item = item;
            this.next = null;
        }
        @Override
        public String toString() {
            if(this.next != null) return this.item.toString() + ">" + this.next.toString();
            else return this.item + "";
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque();
        StdOut.println("Is empty ? " + deque.isEmpty());
        deque.addFirst(3);
        deque.removeFirst();
//        deque.removeLast();
        deque.removeFirst();
        for(int i : deque){
            StdOut.println("Element value is: " + i);
        }
    }
}

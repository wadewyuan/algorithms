import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by wadey on 3/6/2017.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first, last;
    private int size;

    public Deque() {
        size = 0;
    } // construct an empty deque

    public boolean isEmpty() {
        return size() == 0;
    } // is the deque empty?

    public int size() {
        return size;
    } // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Item is null");
        if (isEmpty()) {
            first = new Node<Item>(item);
            last = first;
        } else {
            Node<Item> newFirst = new Node<Item>(item);
            newFirst.next = first;
            first.prev = newFirst;
            first = newFirst;
        }
        size++;
    } // add the item to the front

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Item is null");
        if (isEmpty()) {
            first = new Node<Item>(item);
            last = first;
        } else {
            Node<Item> newLast = new Node<Item>(item);
            last.next = newLast;
            newLast.prev = last;
            last = newLast;
        }
        size++;
    } // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Removing from an empty deque");
        Node<Item> tmpFirst = first;
        Item firstItem = tmpFirst.item;
        first = tmpFirst.next;
        size--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return firstItem;
    } // remove and return the item from the front

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Removing from an empty deque");
        Node<Item> tmpLast = last;
        Item lastItem = tmpLast.item;
        last = tmpLast.prev;
        size--;
        if (isEmpty()) first = null;
        else last.next = null;
        return lastItem;
    } // remove and return the item from the end

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(first);
    } // return an iterator over items in order from front to end

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Removing from iterator is not supported.");
        }
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next, prev;

        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            if (this.next != null) return this.item.toString() + ">" + this.next.toString();
            else return this.item + "";
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println("Is empty ? " + deque.isEmpty());
        deque.addFirst(3);
        deque.addLast(5);
        deque.removeLast();
        deque.removeLast();
//        deque.removeFirst();
        for (int i : deque) {
            StdOut.println("Element value is: " + i);
        }
    }
}

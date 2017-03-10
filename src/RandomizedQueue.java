import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Wade.Yuan on 3/10/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    Item[] items;
    int N;
    int front;
    int rear;

    public RandomizedQueue(int size) {
        N = 0;
        items = (Item[]) new Object[size];
        front = 0;
        rear = -1;
    }                // construct an empty randomized queue
    public boolean isEmpty() {
        return N == 0;
    }                // is the queue empty?
    public int size() {
        return N;
    }                        // return the number of items on the queue
    public void enqueue(Item item) {
        if(N == items.length) throw new UnsupportedOperationException("Queue is full");

        N++;
        if(rear >= items.length - 1) rear = 0;
        else  rear++;
        items[rear] = item;
    }           // add the item
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty");

        N--;
        Item item = items[front];
        items[front] = null;
        if(front >= items.length - 1) front = 0;
        else front++;

        return item;
    }                    // remove and return a random item
    public Item sample() {
        return null;
    }                    // return (but do not remove) a random item
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        int i = front;
        Item current = (Item) items[i];

        @Override
        public boolean hasNext() {
            if(isEmpty()) return false;
            return i == front || i != rear;
        }

        @Override
        public Item next() {
            if(isEmpty()) throw new NoSuchElementException("Queue is empty");
            Item next = (Item) items[i];
            if(i < items.length - 1) i++;
            else i = 0;
            return next;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove from iterator is not supported");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>(5);
        randomizedQueue.enqueue(4);
//        randomizedQueue.enqueue(5);
//        randomizedQueue.enqueue(1);
//        randomizedQueue.enqueue(6);
//        randomizedQueue.enqueue(7);
        randomizedQueue.dequeue();
        randomizedQueue.enqueue(3);
        randomizedQueue.dequeue();
        randomizedQueue.enqueue(2);

        for(Integer i : randomizedQueue) {
            StdOut.println(i);
        }
    }
}

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Wade.Yuan on 3/10/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;
    private int head;
    private int tail;

    public RandomizedQueue() {
        n = 0;
        items = (Item[]) new Object[1];
        head = 0;
        tail = -1;
    } // construct an empty randomized queue

    public boolean isEmpty() {
        return n == 0;
    } // is the queue empty?

    public int size() {
        return n;
    } // return the number of items on the queue

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Item is null");
        if (n == items.length) resize(items.length * 2);
        if (tail >= items.length - 1) tail = 0;
        else tail++;
        items[tail] = item;
        n++;
    } // add the item

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        int rand = StdRandom.uniform(0, n);
        int idx = (head + rand) % items.length;
        if (idx != head) {
            // swap the item to dequeue with the head
            Item tmp = items[idx];
            items[idx] = items[head];
            items[head] = tmp;
        }
        Item item = items[head];
        items[head] = null;
        if (head >= items.length - 1) head = 0;
        else head++;
        n--;
        return item;
    } // remove and return a random item

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return items[(head + StdRandom.uniform(0, n)) % items.length];
    } // return (but do not remove) a random item

    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            temp[i] = items[(head + i) % items.length];
        }
        items = temp;
        head = 0;
        tail = n - 1;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private Item[] q;
        private int c = 0;

        RandomizedQueueIterator() {
            q = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                q[i] = (Item) items[(head + i) % items.length];
            }
            StdRandom.shuffle(q);
        }

        @Override
        public boolean hasNext() {
            return c < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Queue is empty");
            return q[c++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove from iterator is not supported");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(6);
        randomizedQueue.enqueue(7);
//        randomizedQueue.dequeue();
        randomizedQueue.enqueue(3);
//        randomizedQueue.dequeue();
//        randomizedQueue.dequeue();
//        randomizedQueue.dequeue();
        randomizedQueue.enqueue(2);

//        StdOut.println(randomizedQueue.sample());
        while (!randomizedQueue.isEmpty()) {
            StdOut.println(randomizedQueue.dequeue());
        }

        for (Integer i : randomizedQueue) {
            StdOut.println(i);
        }

    }
}

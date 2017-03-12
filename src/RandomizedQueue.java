import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Wade.Yuan on 3/10/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    Item[] items;
    int N;
    int head;
    int tail;

    public RandomizedQueue() {
        N = 0;
        items = (Item[]) new Object[1];
        head = 0;
        tail = -1;
    } // construct an empty randomized queue
    public boolean isEmpty() {
        return N == 0;
    } // is the queue empty?
    public int size() {
        return N;
    } // return the number of items on the queue
    public void enqueue(Item item) {
        if(N == items.length) resize(items.length * 2);
        if(tail >= items.length - 1) tail = 0;
        else  tail++;
        items[tail] = item;
        N++;
    } // add the item
    public Item dequeue() {
        if(isEmpty()) throw new NoSuchElementException("Queue is empty");
        if(N > 0 && N == items.length / 4) resize(items.length / 2);
        int rand = StdRandom.uniform(0, N);
        int idx = (head + rand) % items.length;
        if(idx != head) {
            // swap the item to dequeue with the head
            Item tmp = items[idx];
            items[idx] = items[head];
            items[head] = tmp;
        }
        Item item = items[head];
        items[head] = null;
        if(head >= items.length - 1) head = 0;
        else head++;
        N--;
        return item;
    } // remove and return a random item
    public Item sample() {
        return items[(head + StdRandom.uniform(0, N)) % items.length];
    } // return (but do not remove) a random item

    private void resize(int size) {
        Item[] temp = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) {
            temp[i] = items[(head + i) % items.length];
        }
        items = temp;
        head = 0;
        tail  = N - 1;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        int i = 0;

        @Override
        public boolean hasNext() {
            return i < N;
        }

        @Override
        public Item next() {
            if(!hasNext()) throw new NoSuchElementException("Queue is empty");
            Item next = (Item) items[(head + i) % items.length];
            i++;
            return next;
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

        /*for(Integer i : randomizedQueue) {
            StdOut.println(i);
        }*/
//        StdOut.println(randomizedQueue.sample());
        while (!randomizedQueue.isEmpty()) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}

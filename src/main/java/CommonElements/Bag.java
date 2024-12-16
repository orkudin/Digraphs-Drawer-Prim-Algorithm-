package CommonElements;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Bag class implementation
class Bag<T> implements Iterable<T> {
    private Node first; // first node in bag
    private int size;

    private class Node {
        T item;
        Node next;
    }

    public Bag() {
        first = null;
        size = 0;
    }

    public void add(T item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    public int Size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Bag.Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = (T) current.item;
            current = current.next;
            return item;
        }
    }
}

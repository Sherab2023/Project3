package project3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of a sorted doubly-linked list.
 * All elements in the list are maintained in ascending/increasing order
 * based on the natural order of the elements.
 * This list does not allow <code>null</code> elements.
 *
 * @author Joanna Klukowska
 * @author Sherab
 *
 * @param <E> the type of elements held in this list
 */
public class SortedLinkedList<E extends Comparable<E>> implements Iterable<E> {

    private Node head;
    private Node tail;
    private int size;


    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adds the specified element to the list in ascending order.
     *
     * @param element the element to add
     * @return <code>true</code> if the element was added successfully,
     * <code>false</code> otherwise (if <code>element==null</code>)
     */
    public boolean add(E element) {
        if (element == null) {
            return false; // Disallow null elements
        }

        Node newNode = new Node(element);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            Node current = head;
            Node prev = null;

            while (current != null && element.compareTo(current.data) > 0) {
                prev = current;
                current = current.next;
            }
            if (prev == null) {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            } else if (current == null) {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            } else {
                prev.next = newNode;
                newNode.prev = prev;
                newNode.next = current;
                current.prev = newNode;
            }
        }

        size++;
        return true;
    }

    /**
     * Removes all elements from the list.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns <code>true</code> if the list contains the specified element,
     * <code>false</code> otherwise.
     *
     * @param o the element to search for
     * @return <code>true</code> if the element is in the list,
     * <code>false</code> otherwise
     */
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns the element at the specified index in the list.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of
     *                                   range <code>(index < 0 || index >= size())</code>
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the element is not in the list.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the element,
     * or -1 if the element is not in the list
     */
    public int indexOf(Object o) {
        if (o == null) {
            return -1;
        }

        Node current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(o)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * starting at the specified <code>index</code>, or -1 if the element is not in the list
     * in the range of indexes <code>index <= i < size()</code>.
     *
     * @param o     the element to search for
     * @param index the index to start searching from
     * @return the index of the first occurrence of the element, starting at the specified index,
     * or -1 if the element is not found
     */
    public int nextIndexOf(Object o, int index) {
        if (o == null || index < 0 || index >= size) {
            return -1;
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            if (current != null) {
                current = current.next;
            } else {
                return -1; // Index out of range
            }
        }

        int nextIndex = index;
        while (current != null) {
            if (current.data.equals(o)) {
                return nextIndex;
            }
            current = current.next;
            nextIndex++;
        }

        return -1;
    }

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param o the element to remove
     * @return <code>true</code> if the element was removed successfully,
     * <code>false</code> otherwise
     */
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }

        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                Node prev = current.prev;
                Node next = current.next;
                if (prev != null) {
                    prev.next = next;
                } else {
                    head = next;
                }
                if (next != null) {
                    next.prev = prev;
                } else {
                    tail = prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return true if the linked list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an iterator over the elements in the list.
     *
     * @return an iterator over the elements in the list
     */
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Compares the specified object with this list for equality.
     *
     * @param o the object to compare with
     * @return <code>true</code> if the specified object is equal to this list,
     * <code>false</code> otherwise
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SortedLinkedList)) {
            return false;
        }
        SortedLinkedList<?> otherList = (SortedLinkedList<?>) o;

        if (this.size() != otherList.size()) {
            return false;
        }

        Iterator<E> thisIterator = this.iterator();
        Iterator<?> otherIterator = otherList.iterator();

        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            E thisElement = thisIterator.next();
            Object otherElement = otherIterator.next();

            if (!thisElement.equals(otherElement)) {
                return false;
            }
        }

        return !thisIterator.hasNext() && !otherIterator.hasNext();
    }


    /**
     * Returns a string representation of the list.
     * The string representation consists of a list of the lists's elements in
     * ascending order, enclosed in square brackets ("[]").
     * Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of the list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }


    /**
     * Inner class to represent nodes of this list.
     */
    private class Node {
        E data;
        Node next;
        Node prev;

        Node(E data) {
            if (data == null) {
                throw new NullPointerException("does not allow null");
            }
            this.data = data;
        }

        @SuppressWarnings("unused")
		Node(E data, Node next, Node prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * A basic forward iterator for this list.
     */
    private class ListIterator implements Iterator<E> {
        Node nextToReturn = head;

        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (nextToReturn == null) {
                throw new NoSuchElementException("the end of the list reached");
            }
            E tmp = nextToReturn.data;
            nextToReturn = nextToReturn.next;
            return tmp;
        }


    }
    
}
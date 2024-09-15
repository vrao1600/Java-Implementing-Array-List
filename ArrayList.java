package DataStructures;

import Exceptions.*;
import ADTs.*;

/**
 * A dynamic array implementation of the ListADT interface.
 *
 * @param <T> The type of elements stored in the list. T must implement the Comparable interface.
 */
public class ArrayList<T extends Comparable<T>> implements ListADT<T> {

    /**
     * the number of elements in the array list
     */
    int size;
    /**
     * the capacity of the array list
     */
    int capacity;
    /**
     * the array used to store the elements in the list
     */
    T[] buffer;

    /**
     * Default constructor initializing the array list with a default capacity of 10.
     */
    public ArrayList() {
        size = 0;
        capacity = 10;
        buffer = (T[]) new Comparable[capacity];
    }

    /**
     * Constructor initializing the array list with the specified capacity.
     * 
     * @param capacity the initial capacity of the array list
     */
    public ArrayList(int capacity) {
        size = 0;
        this.capacity = capacity;
        buffer = (T[]) new Comparable[capacity];
    }

    /**
     * Checks if the array list is empty.
     * 
     * @return true if the array list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the array list.
     * 
     * @return the size of the array list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Adds an element to the front of the array list.
     * 
     * @param element the element to be added
     */
    @Override
    public void addFirst(T element) {
        ensureCapacity();
        for (int i = size; i > 0; i--) {
            buffer[i] = buffer[i - 1];
        }
        buffer[0] = element;
        size++;
    }

    /**
     * Adds an element to the end of the array list.
     * 
     * @param element the element to be added
     */
    @Override
    public void addLast(T element) {
        ensureCapacity();
        buffer[size] = element;
        size++;
    }

    /**
     * Adds an element after an existing element in the array list.
     * 
     * @param existing the existing element to add after
     * @param element  the element to be added
     * @throws ElementNotFoundException if the existing element is not found
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public void addAfter(T existing, T element) throws ElementNotFoundException, EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        int index = indexOf(existing);
        if (index == -1) {
            throw new ElementNotFoundException(existing.toString());
        }

        ensureCapacity();
        for (int i = size; i > index + 1; i--) {
            buffer[i] = buffer[i - 1];
        }
        buffer[index + 1] = element;
        size++;
    }

    /**
     * Removes an element from the array list.
     * 
     * @param element the element to be removed
     * @return the removed element
     * @throws EmptyCollectionException if the collection is empty
     * @throws ElementNotFoundException if the element is not found
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        int index = indexOf(element);
        if (index == -1) {
            throw new ElementNotFoundException(element.toString());
        }

        T removedElement = buffer[index];
        for (int i = index; i < size - 1; i++) {
            buffer[i] = buffer[i + 1];
        }
        buffer[size - 1] = null;
        size--;
        return removedElement;
    }

    /**
     * Removes the first element from the array list.
     * 
     * @return the removed first element
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        T removedElement = buffer[0];
        for (int i = 0; i < size - 1; i++) {
            buffer[i] = buffer[i + 1];
        }
        buffer[size - 1] = null;
        size--;
        return removedElement;
    }

    /**
     * Removes the last element from the array list.
     * 
     * @return the removed last element
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        T removedElement = buffer[size - 1];
        buffer[size - 1] = null;
        size--;
        return removedElement;
    }

    /**
     * Returns the first element in the array list without removing it.
     * 
     * @return the first element in the array list
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        return buffer[0];
    }

    /**
     * Returns the last element in the array list without removing it.
     * 
     * @return the last element in the array list
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        return buffer[size - 1];
    }

    /**
     * Checks if the array list contains a specific element.
     * 
     * @param element the element to check for
     * @return true if the element is found, false otherwise
     * @throws EmptyCollectionException if the collection is empty
     */
    @Override
    public boolean contains(T element) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        return indexOf(element) != -1;
    }

    /**
     * Returns the index of a specific element in the array list.
     * 
     * @param element the element to find
     * @return the index of the element, or -1 if not found
     */
    @Override
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (buffer[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the element at a specific index in the array list.
     * 
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws EmptyCollectionException if the collection is empty
     * @throws InvalidArgumentException if the index is out of bounds
     */
    @Override
    public T get(int index) throws EmptyCollectionException, InvalidArgumentException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        if (index < 0 || index >= size) {
            throw new InvalidArgumentException("Index out of bounds");
        }

        return buffer[index];
    }

    /**
     * Sets the element at a specific index in the array list.
     * 
     * @param index   the index to set the element at
     * @param element the element to set
     * @throws EmptyCollectionException if the collection is empty
     * @throws InvalidArgumentException if the index is out of bounds
     */
    @Override
    public void set(int index, T element) throws EmptyCollectionException, InvalidArgumentException {
        if (isEmpty()) {
            throw new EmptyCollectionException("ArrayList");
        }

        if (index < 0 || index >= size) {
            throw new InvalidArgumentException("Index out of bounds");
        }

        buffer[index] = element;
    }

    /**
     * Ensures the array list has enough capacity to store new elements.
     * If the current capacity is reached, the capacity is doubled.
     */
    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            T[] newBuffer = (T[]) new Comparable[capacity];
            System.arraycopy(buffer, 0, newBuffer, 0, size);
            buffer = newBuffer;
        }
    }

    /**
     * Returns a string representation of the array list.
     * 
     * @return a string representation of the array list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(buffer[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
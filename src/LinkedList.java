import java.util.*;

public class LinkedList<T> implements List<T> {

    private Item<T> firstInList = null;

    private Item<T> lastInList = null;

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        // BEGIN (write your solution here)
        for (T item: this) {
            if (o != null && o.equals(item)) return true;
            if (o == null && item == null) return true;
        }
        return false;
        // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator(0);
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        Object[] arr = new Object[size];
        int index = 0;
        for (T item: this) {
            arr[index++] = item;
        }
        return arr;
        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        if (a.length >= size) {
            for (int i = 0; i < size; i++) {
                a[i] = (T1) get(i);
            }
            for (int i = size; i < a.length; i++) {
                a[i] = null;
            }
            return a;
        } else {
            T1[] result = (T1[]) java.lang.reflect.Array
                    .newInstance(a.getClass().getComponentType(), size);
            for (int i = 0; i < size; i++) {
                result[i] = (T1) get(i);
            }
            return result;
        }
        // END
    }

    @Override
    public boolean add(final T newElement) {
        // BEGIN (write your solution here)
        if (size == 0) {
            Item newItem = new Item(newElement, null, null);
            this.firstInList = newItem;
            this.lastInList = newItem;
        } else {
            Item newItem = new Item(newElement, this.lastInList, null);
            this.lastInList.nextItem = newItem;
            this.lastInList = newItem;
        }
        size++;
        return true;
        // END
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Object o) {
        // BEGIN (write your solution here)
        int index = indexOf(o);
        if (index == -1) return false;
        remove(index);
        return true;
        // END
    }

    private Item<T> getItem(int index) {
        Item<T> toRemove = firstInList;
        int i = 0;
        while (i < index) {
            toRemove = toRemove.getNextItem();
            i++;
        }
        return toRemove;
    }

    @Override
    public T remove(final int index) throws IndexOutOfBoundsException {
        // BEGIN (write your solution here)
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Item<T> toRemove = getItem(index);
        remove(toRemove);
        return (T) toRemove.element;
        // END
    }

    private void remove(final Item<T> toRemove) {
        // BEGIN (write your solution here)
        if (toRemove.getNextItem() != null) toRemove.getNextItem().prevItem = toRemove.getPrevItem();
        else this.lastInList = toRemove.getPrevItem();
        if (toRemove.getPrevItem() != null) toRemove.getPrevItem().nextItem = toRemove.getNextItem();
        else this.firstInList = toRemove.getNextItem();
        size--;
        // END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        this.removeIf(item -> !c.contains(item));
        return true;
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        this.firstInList = null;
        this.lastInList = null;
        this.size = 0;
        // END
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return null;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object o) {
        // BEGIN (write your solution here)
        int index = 0;
        Item<T> obj = firstInList;
        while (obj != null) {
            if (o != null && o.equals(obj.element)) return index;
            if (o == null && obj.element == null) return index;
            index++;
            obj = obj.getNextItem();
        }
        return -1;
        // END
    }

    @Override
    public T set(final int index, final T element) {
        // BEGIN (write your solution here)
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        Item<T> toReplace = getItem(index);
        T tmp = toReplace.element;
        toReplace.element = element;
        return tmp;
        // END
    }

    @Override
    public T get(final int index) {
        // BEGIN (write your solution here)
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        Item<T> result = getItem(index);
        return result.element;
        // END
    }


    private class ElementsIterator implements ListIterator<T> {

        private Item<T> currentItemInIterator;

        private Item<T> lastReturnedItemFromIterator;

        private int index;


        ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            this.index = index;
            currentItemInIterator = (index == size) ? null : getItem(index);
            // END
        }

        @Override
        public boolean hasNext() {
            // BEGIN (write your solution here)
            return index < LinkedList.this.size();
            // END
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            if (!hasNext()) throw new NoSuchElementException();
            lastReturnedItemFromIterator = currentItemInIterator;
            currentItemInIterator = currentItemInIterator.getNextItem();
            index++;
            return lastReturnedItemFromIterator.element;
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            return index > 0;
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if (!hasPrevious()) throw new NoSuchElementException();
            lastReturnedItemFromIterator = getItem(previousIndex());
            index--;
            currentItemInIterator = getItem(index);
            return lastReturnedItemFromIterator.element;
            // END
        }

        @Override
        public void add(final T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if (lastReturnedItemFromIterator == null) throw new IllegalStateException();
            lastReturnedItemFromIterator.element = element;
            // END
        }

        @Override
        public int previousIndex() {
            // BEGIN (write your solution here)
            return index - 1;
            // END
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return index;
            // END
        }

        @Override
        public void remove() {
            // BEGIN (write your solution here)
            if (lastReturnedItemFromIterator == null) throw new IllegalStateException();
            LinkedList.this.remove(lastReturnedItemFromIterator);
            lastReturnedItemFromIterator = null;
            index--;
            // END
        }
    }

    private static class Item<T> {

        private T element;

        private Item<T> nextItem;

        private Item<T> prevItem;

        Item(final T element, final Item<T> prevItem, final Item<T> nextItem) {
            this.element = element;
            this.nextItem = nextItem;
            this.prevItem = prevItem;
        }

        public Item<T> getNextItem() {
            return nextItem;
        }

        public Item<T> getPrevItem() {
            return prevItem;
        }
    }
}
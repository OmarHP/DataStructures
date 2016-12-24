import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 22/12/2016.
 */
public class PriorityQueue<T extends Comparable<T>> {

    private List<T> data;

    public static enum Priority {
        MAX,
        MIN
    }

    private Priority priority = Priority.MAX;

    public PriorityQueue() {
        data = new ArrayList<T>();
    }

    public PriorityQueue(Priority priority) {
        data = new ArrayList<T>();
        this.priority = priority;
    }

    public void insert(T element) {
        data.add(element);
        siftUp(data.size() - 1);
    }

    public T extract() {


        if (data.size() > 1) {
            T result = data.get(0);
            T tmp = data.remove(data.size() - 1);
            data.set(0, tmp);
            siftDown(0);
            return result;
        } else {
            return data.remove(0);
        }
    }

    private boolean comparePriority(T o1, T o2) {
        if (priority == Priority.MAX) {
            return o1.compareTo(o2) > 0;
        } else {
            return o1.compareTo(o2) < 0;
        }
    }

    private void siftUp(int i) {
        while (i > 0 && comparePriority(data.get(i), data.get(parent(i)))) {
            swap(parent(i), i);
            i = parent(i);
        }
    }

    private void siftDown(int i) {
        int maxIndex = i;

        int l = leftChild(i);
        if (l < data.size() && comparePriority(data.get(l), data.get(maxIndex))) {
            maxIndex = l;
        }

        int r = rightChild(i);
        if (r < data.size() && comparePriority(data.get(r), data.get(maxIndex))) {
            maxIndex = r;
        }

        if (i != maxIndex) {
            swap(i, maxIndex);
            siftDown(maxIndex);
        }

    }

    private void swap(int i, int j) {
        T tmp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, tmp);
    }

    private int parent(int i) {
        return (int) (i / 2.0 - 0.5);
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    public int size() {
        return data.size();
    }



    public static void main(String... args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Priority.MIN);
        queue.insert(1);
        queue.insert(10);
        queue.insert(200);
        queue.insert(15);
        queue.insert(25);
        queue.insert(5);
        queue.insert(100);

        while (queue.size() > 0) {
            System.out.println(queue.extract());
        }


    }

}

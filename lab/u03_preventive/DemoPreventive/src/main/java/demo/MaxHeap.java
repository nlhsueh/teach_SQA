package demo;

public class MaxHeap {
    private int[] heap;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
    }

    private int getParentIndex(int index) {
        return (index-1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void swap(int index1, int index2) {
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public void insert(int value) {
        if (size >= capacity) {
            System.out.println("Heap is full. Cannot insert.");
            return;
        }

        int currentIndex = size;
        heap[currentIndex] = value;
        size++;

        // Perform "bubble-up" to maintain the max-heap property
        while (currentIndex > 0 && heap[currentIndex] > heap[getParentIndex(currentIndex)]) {
            swap(currentIndex, getParentIndex(currentIndex));
//            currentIndex = currentIndex-1;
            currentIndex = getParentIndex(currentIndex);
        }
    }

    public int extractMax() {
        if (size == 0) {
            System.out.println("Heap is empty. Cannot extract max.");
            return -1;
        }

        int max = heap[0];

        // Replace the root with the last element
        heap[0] = heap[size - 1];
        size--;

        // Perform "bubble-down" to maintain the max-heap property
        int currentIndex = 0;
        while (true) {
            int leftChildIndex = getLeftChildIndex(currentIndex);
            int rightChildIndex = getRightChildIndex(currentIndex);
            int largestIndex = currentIndex;

            if (leftChildIndex < size && heap[leftChildIndex] > heap[largestIndex]) {
                largestIndex = leftChildIndex;
            }

            if (rightChildIndex < size && heap[rightChildIndex] > heap[largestIndex]) {
                largestIndex = rightChildIndex;
            }

            if (largestIndex == currentIndex) {
                break; // Heap property is restored
            }

            swap(currentIndex, largestIndex);
            currentIndex = largestIndex;
        }

        return max;
    }

    public void printHeap() {
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MaxHeap maxHeap = new MaxHeap(10);

        System.out.println("Insert 5 3 8 1 10 20 30");
        maxHeap.insert(5);
        maxHeap.printHeap();
        maxHeap.insert(3);
        maxHeap.printHeap();
        maxHeap.insert(8);
        maxHeap.printHeap();
        maxHeap.insert(1);
        maxHeap.printHeap();
        maxHeap.insert(10);
        maxHeap.printHeap();
        maxHeap.insert(20);
        maxHeap.printHeap();
        maxHeap.insert(30);

        System.out.println("Max Heap:");
        maxHeap.printHeap();

        System.out.println("Extracted Max: " + maxHeap.extractMax());
        System.out.println("Updated Max Heap:");
        maxHeap.printHeap();

        System.out.println("Extracted Max: " + maxHeap.extractMax());
        System.out.println("Updated Max Heap:");
        maxHeap.printHeap();

        System.out.println("Extracted Max: " + maxHeap.extractMax());
        System.out.println("Updated Max Heap:");
        maxHeap.printHeap();
    }
}
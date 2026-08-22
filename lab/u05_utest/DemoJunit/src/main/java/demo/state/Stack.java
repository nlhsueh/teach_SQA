package demo.state;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class Stack<T> {
    private final LinkedList<T> elements = new LinkedList<>();
    private static final int MAX_SIZE = 5;  // Maximum stack size

    public static void main(String[] arg) {
        Stack<Object> stack = new Stack<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        stack.push("5");
        stack.push("6");

    }

    // Pushes an element onto the stack
    public void push(T element) {
        if (elements.size() >= MAX_SIZE) {
            throw new IllegalStateException("Stack overflow: maximum size of 5 elements reached.");
        }
        elements.addFirst(element);
    }

    public boolean isFull() {
        return elements.size() == MAX_SIZE;
    }

    // Pops the top element from the stack
    public T pop() {
        if (elements.isEmpty()) {
            throw new EmptyStackException();
        }
        System.out.println(elements);
        return elements.removeFirst();
    }

    // Peeks at the top element of the stack without removing it
    public T peek() {
        if (elements.isEmpty()) {
            throw new EmptyStackException();
        }
        return elements.getFirst();
    }

    // Checks if the stack is empty
    public boolean isEmpty() {
        return elements.isEmpty();
    }
}

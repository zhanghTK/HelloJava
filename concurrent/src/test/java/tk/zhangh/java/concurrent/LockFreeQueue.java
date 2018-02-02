package tk.zhangh.java.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 非阻塞队列
 * Created by ZhangHao on 2018/1/16.
 */
public class LockFreeQueue<T> {
    private static class Node<E> {
        E value;
        volatile Node<E> next;

        Node(E value) {
            this.value = value;
        }
    }

    private AtomicReference<Node<T>> head, tail;

    public LockFreeQueue() {
        Node<T> dummyNode = new Node<>(null);
        head = new AtomicReference<>(dummyNode);
        tail = new AtomicReference<>(dummyNode);
    }

    public void putObject(T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> preTailNode = tail.getAndSet(newNode);
        preTailNode.next = newNode;
    }

    public T getObject() {
        Node<T> headNode, headNextNode;
        do {
            headNode = head.get();
            headNextNode = headNode.next;
        } while (headNextNode != null && !head.compareAndSet(headNode, headNextNode));
        T value = (headNextNode != null ? headNextNode.value : null);
        if (headNextNode != null) {
            headNextNode.value = null;
        }
        return value;
    }
}

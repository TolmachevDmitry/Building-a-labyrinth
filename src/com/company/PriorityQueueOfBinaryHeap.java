package com.company;

import java.util.ArrayList;

public class PriorityQueueOfBinaryHeap<T extends Comparable<T>> {
//    private T[] arrOfHeap = (T[])(new Object[10000]);

    private class Node {
        public T value;
        double priority;

        Node(T value, double priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    ArrayList<Node> binaryHeap = new ArrayList<Node>();
    private int size = 0;

    public void insert(T value, double priority) {

        binaryHeap.add(new Node(value, priority));
        size++;

        if (size > 1) {
            siftingElem(size - 1);
        }

    }

    public T extractMin() {
        Node elem = binaryHeap.get(0);
        size--;

        if (size <= 1) {
            binaryHeap.remove(0);
        } else {
            Node t = binaryHeap.get(size);
            binaryHeap.set(0, t);
            binaryHeap.remove(size);

            loweringElem();
        }

        return elem.value;
    }

    private void loweringElem() {
        int index = 0;

        while(true) {
//            System.out.println("Hi!");
            Node ourElem = binaryHeap.get(index);

            if (2 * index + 2 < size) {

                Node descendant1 = binaryHeap.get(2 * index + 1);
                Node descendant2 = binaryHeap.get(2 * index + 2);

                if (ourElem.priority < descendant1.priority && ourElem.priority < descendant2.priority) {
                    break;
                }
                if (descendant1.priority <= descendant2.priority) {
                    binaryHeap.set(2 * index + 1, ourElem);
                    binaryHeap.set(index, descendant1);
                    index = 2 * index + 1;
                } else if (descendant2.priority <= descendant1.priority) {
                    binaryHeap.set(2 * index + 2, ourElem);
                    binaryHeap.set(index, descendant2);
                    index = 2 * index + 2;
                }

            } else if (2 * index + 1 < size) {
                 Node descendant = binaryHeap.get(2 * index + 1);

                 if (ourElem.priority < descendant.priority) {
                     break;
                 }
                 binaryHeap.set(2 * index + 1, ourElem);
                 binaryHeap.set(index, descendant);
                 index = 2 * index + 1;

            } else {
                break;
            }
        }


    }

    public void increase(T value, double upDatedPriority) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            Node t = binaryHeap.get(i);
            if (t.value.compareTo(value) == 0) {
                // Нашли индекс элемента, приоритет которого нужно понизить
                index = i;
                break;
            }
        }

        if (index != -1) {
            binaryHeap.set(index, new Node(value, upDatedPriority));
            siftingElem(index);
        }
    }

    private void siftingElem(int startIndex) {

        int index = startIndex;

        while (true) {
            if (index == 0) {
                break;
            }

            int indexOfParent = (index % 2 == 0) ? ((index - 1) / 2) : (index / 2);

            Node ourElem = binaryHeap.get(index);
            Node parent = binaryHeap.get(indexOfParent);

            if (parent.priority > ourElem.priority) {
                binaryHeap.set(indexOfParent, ourElem);
                binaryHeap.set(index, parent);
                index = indexOfParent;
            } else {
                break;
            }
        }
    }

    public int size() {
        return size;
    }

    public void test() {
        for (int i = 0; i < size; i++) {
            System.out.print(binaryHeap.get(i).value + " - " + binaryHeap.get(i).priority
                    + ((i != size - 1) ? (", ") : (";")));
        }
        System.out.println();
    }

    public ArrayList<T> getValues() {
        ArrayList<T> answer = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            answer.add(binaryHeap.get(i).value);
        }
        return answer;
    }
    public ArrayList<Double> getPriorities() {
        ArrayList<Double> answer = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            answer.add(binaryHeap.get(i).priority);
        }
        return answer;
    }

}

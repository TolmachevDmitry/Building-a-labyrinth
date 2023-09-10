package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GraphsAlgorithms {

    private int randomWeight(int a, int b) {
        int randNum = -1;
        randNum = a + (int) (Math.random() * (b - a));

        return randNum;
    }

    public Graph createGrid(int n) {
        int vCount = n;
        int nodeCount = (int) Math.sqrt(vCount);

        Graph g = new Graph();

        for (int v = vCount; v > 0; v--) {
            if (v + nodeCount <= vCount) {
                g.addEdge(v, v + nodeCount, randomWeight(1, 200));
            }
            if ((v - 1) % nodeCount != 0) {
                g.addEdge(v, v - 1, randomWeight(1, 200));
            }
        }

//        g.outMatrix();
        return g;
    }

    public ArrayList<PointKey> prim(Graph g) {
        // Инициализация исходных данных
        ArrayList<PointKey> answer = new ArrayList<>();
        PriorityQueueOfBinaryHeap<Integer> q = new PriorityQueueOfBinaryHeap<>();
        HashSet<Integer> mapInQueue = new HashSet<>();

        for (int i = 1; i <= g.vertexCount(); i++) {
            if (i == 1) {
                answer.add(new PointKey(0, 0));
                q.insert(i, 0);
            } else {
                answer.add(new PointKey(0, -Double.NEGATIVE_INFINITY));
                q.insert(i, -Double.NEGATIVE_INFINITY);
            }
            mapInQueue.add(i);
        }

        // Сам алгоритм
        while(q.size() != 0) {

            int v = q.extractMin();
            mapInQueue.remove(v);
            for (int u = 1; u <= g.vertexCount(); u++) {
                int w = g.getWeight(v, u);
                PointKey pk = answer.get(u - 1);
                if (v != u && mapInQueue.contains(u) && w != 0 && pk.key > w) {
                    pk.p = v;
                    pk.key = w;
                    q.increase(u, pk.key);
                }
            }
        }

//        int len = answer.toArray().length;
//        for (int i = 0; i < len; i++) {
//            PointKey pk = answer.get(i);
//            System.out.println((i + 1) + "| point:" + pk.p + "; key: " + pk.key);
//        }


        return answer;
    }

}

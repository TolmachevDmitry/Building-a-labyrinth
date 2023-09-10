package com.company;

import java.util.Arrays;

public class Graph {

    private int[][] adjMatrix = null;
    private int vCount = 0;
    private int eCount = 0;

    public void addEdge(int v1, int v2, int weight) {
        int maxV = Math.max(v1, v2);
        int minV = Math.min(v1, v2);

        if (maxV > vCount && minV > 0) {
            int[][] t = new int[maxV][maxV];
            for (int i = 0; i < vCount; i++) {
                t[i] = Arrays.copyOf(adjMatrix[i], maxV);
            }
            adjMatrix = t;
        }
        if (minV > 0) {
            adjMatrix[v1 - 1][v2 - 1] = weight;
            adjMatrix[v2 - 1][v1 - 1] = weight;

            if (maxV > vCount) {
                vCount += (maxV > vCount) ? (maxV - vCount) : (0);
            }
            eCount += 1;
        }
    }

    public void removeEdge(int v1, int v2) {
        if (Math.max(v1, v2) <= vCount && Math.min(v1, v2) > 0) {
            adjMatrix[v1 - 1][v2 - 1] = 0;
            adjMatrix[v2 - 1][v1 - 1] = 0;
            eCount -= 1;
        }
    }

    public int getWeight(int v1, int v2) {
        if (Math.max(v1, v2) <= vCount && Math.min(v1, v2) > 0) {
            return adjMatrix[v1 - 1][v2 - 1];
        } else {
            return 0;
        }
    }

    public int vertexCount() {
        return vCount;
    }

    public int edgeCount() {
        return eCount;
    }

    // Для проверки
    public void outMatrix() {
        for (int i = 0; i < vCount; i++) {
            for (int j = 0; j < vCount; j++) {
                if (j == 0) {
                    System.out.print((i + 1) + ": [");
                }
                System.out.print(adjMatrix[i][j] + ((j != vCount - 1) ? (" ") : ("")));
                if (j == vCount - 1) {
                    System.out.print("]");
                }
            }
            System.out.println();
        }
    }

    public void outMatrix1() {
        for (int i = 0; i < vCount; i++) {
            System.out.println(i + ": " + adjMatrix[i].length);
        }
    }

}

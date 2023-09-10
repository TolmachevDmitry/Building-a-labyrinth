package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Draw extends JFrame {
    private JPanel mainPanel;
    private JButton Button1;
    private JTextField graphSize;
    private JTextField squareSize;
    private JButton Button2;

    private Graph graph = new Graph();
    private ArrayList<PointKey> tree;

    private int screenX = 0;
    private int screenY = 0;

    private int tX;
    private int tY;

    private int n;
    private int step;

    private boolean graphIsMade = false;

    GraphsAlgorithms graphsAlgorithms = new GraphsAlgorithms();

    public Draw() {
        this.setTitle("Лабиринт");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1000, 700);

        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                n = getInteger(graphSize);
                step = getInteger(squareSize);

                graph = graphsAlgorithms.createGrid(n * n);
                tree = graphsAlgorithms.prim(graph);

                graphIsMade = true;
                callRepaint();
            }
        });

        Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (getInteger(squareSize) < 4) {
                    step = 4;
                } else {
                    step = getInteger(squareSize);
                }

                callRepaint();
            }
        });

        mainPanel.addMouseListener(new MouseEvents() {
            @Override
            public void mousePressed(MouseEvent e) {
                tX = e.getX();
                tY = e.getY();
            }
        });

        mainPanel.addMouseMotionListener(new MouseEvents() {
            public void mouseDragged(MouseEvent e) {
                int delX = e.getX() - tX;
                int delY = e.getY() - tY;

                if (delX != 0 || delY != 0) {
                    screenX += delX;
                    screenY += delY;

                    callRepaint();
                }

                tX = e.getX();
                tY = e.getY();
            }

        });

    }

    private int getInteger(JTextField te) {
        return Integer.parseInt(te.getText());
    }

    private int getProbability(int n) {
        if (n >= 100) {
            return 95;
        }
        return n;
    }

    // Т.к. в теле условий repaint() не вызывается...
    public void callRepaint() {
        repaint();
    }

    private void drawSquare(int x1, int y1, int x2, int y2, Graphics gr) {
        int d = step / 4;

        if (x1 < x2) {
            gr.fillRect(x1 - d, y1 - d, step + 2 * d, 2 * d);
        }
        if (x1 > x2) {
            gr.fillRect(x2 - d, y1 - d, step + 2 * d, 2 * d);
        }
        if (y1 < y2) {
            gr.fillRect(x1 - d, y1 - d, 2 * d, step + 2 * d);
        }
        if (y1 > y2) {
            gr.fillRect(x1 - d, y2 - d, 2 * d, step + 2 * d);
        }
    }

    private void drawGraph(Graphics gr) {
        int xStart = 350 + screenX;
        int yStart = 100 + screenY;

        int line = n;

        gr.setColor(new Color(0x008C00));
        gr.fillRect(xStart - step * 3 / 4, yStart - step * 3 / 4, step * line + (step / 2),
                step * line + (step / 2));

        // Оставное дерево
        gr.setColor(Color.WHITE);
        for (int i = 0, y = yStart, v = 1; i < line; i++, y += step) {
            for (int j = 0, x = xStart; j < line; j++, v++, x += step) {
//                gr.fillRect(x, y, step, step);

                PointKey pk = tree.get(v - 1);
                if (v != 1 && pk.p == v - 1) {
                    drawSquare(x - step, y, x, y, gr);
                }
                if (pk.p == v + 1) {
                    drawSquare(x + step, y, x, y, gr);
                }
                if (pk.p == v + line) {
                    drawSquare(x, y + step, x, y, gr);
                }
                if (pk.p == v - line) {
                    drawSquare(x, y - step , x, y, gr);
                }
            }
        }

    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gr = (Graphics2D) g;

        if (graphIsMade) {
            drawGraph(gr);
        }

    }

}

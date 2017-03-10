/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.io.*;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author ssc3
 */
public class Graph {

    class Node {

        String val;
        ArrayList<Node> children;
        ArrayList<Node> parents;

        boolean installed;

        Node(String str) {
            val = str;
            installed = false;
            children = new ArrayList<>();
            parents = new ArrayList<>();
        }
    }

    HashMap<String, Node> allNodes = new HashMap<>();

    public void addToGraph(String[] tokens) {
        if (tokens.length == 1) {
            return;
        }

        Node curHead = null;
        for (int i = 1; i < tokens.length; i++) {

            Node newNode = new Node(tokens[i]);
            if (!allNodes.containsKey(tokens[i])) {
                allNodes.put(tokens[i], newNode);
            } else {
                newNode = allNodes.get(tokens[i]);
            }
            if (i == 1) {
                curHead = newNode;
                continue;
            }

            if (i > 1) {
                curHead.parents.add(newNode);
                newNode.children.add(curHead);
            }

        }
    }

    public void install(Node installNode) {

        if (installNode != null) {
            for (Node parent : installNode.parents) {
                install(parent);
            }
        }

        if (installNode.installed == false) {
            installNode.installed = true;
            System.out.println("    Installing " + installNode.val);
        }
    }

    public void remove(Node n) {
        if (n == null) {
            return;
        }

        if (n.children.size() == 0) {
            n.installed = false;
            System.out.println("    Removing " + n.val);
            allNodes.remove(n.val);
            for (Node parent : n.parents) {
                parent.children.remove(n);
                remove(parent);
            }
        }

    }

    public void printInstalled() {
        for (Node n : allNodes.values()) {
            if (n.installed) {
                System.out.println("    " + n.val);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        BufferedReader br = null;

        Graph graph = new Graph();

        try {
            br = new BufferedReader(new FileReader("input.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] tokens = line.split(" ");

                    switch (tokens[0]) {
                        case "DEPEND":
                            System.out.println(line);
                            graph.addToGraph(tokens);

                            break;
                        case "INSTALL":
                            System.out.println(line);
                            Node installNode = graph.allNodes.get(tokens[1]);
                            if (installNode == null) {
                                graph.addToGraph(tokens);
                            }
                            installNode = graph.allNodes.get(tokens[1]);

                            if (installNode.installed) {
                                System.out.println("    " + installNode.val + " is already installed");
                            } else {
                                graph.install(installNode);
                            }
                            break;
                        case "REMOVE":
                            System.out.println(line);
                            Node removeNode = graph.allNodes.get(tokens[1]);
                            if (removeNode == null) {
                                break;
                            }

                            if (removeNode.children.size() > 0) {
                                System.out.println("    " + removeNode.val + " is still needed");
                            } else {
                                graph.remove(removeNode);
                            }

                            break;
                        case "LIST":
                            System.out.println(line);
                            graph.printInstalled();

                            break;

                        case "END":
                            System.out.println(line);
                            break;
                        default:
                            System.out.println("Unsupported op");
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

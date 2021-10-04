import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Node {
    Node left, right;
    int value;

    Node(int value) {
        left = right = null;
        this.value = value;
    }
}

public class BiTree2 {
    
    Node root;

    int height() {
        return height(root);
    }

    int height(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(height(node.left), height(node.right));
    }


    boolean isLeaf(Node node) {
        if (node.left == null && node.right == null) {
            return true;
        }
        return false;
    }

    boolean isBalanced (Node node) {
        int left;
        int right;

        if (node == null) {
            return true;
        }

        left = height(node.left);
        right = height(node.right);

        if (Math.abs(left - right) <= 1 && isBalanced(node.left) && isBalanced(node.right)) {
            return true;
        }
        return false;
    }

    // isBST - Pseudocode
    // Recursively search left and right subtrees, all nodes in the left subtree are less and all nodes in right subtree are greater than root.
    // boolean isBST(Node node)
    // Validate root
    // ...

    boolean isBST() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    boolean isBST(Node node, int min, int max) {
        if (node == null) {
            return true;
        }

        if (node.value < min || node.value > max) {
            return false;
        }

        return isBST(node.left, min, node.value - 1) && isBST(node.right, node.value + 1, max);
    }

    
    void printLevel(int level) {
        printLevel(root, level);
    }

    void printLevel(Node node, int level) {
        if (node == null) {
            return;
        }
        if (level == 1) {
            System.out.print(node.value + " ");
        }
        else if (level > 1) {
            printLevel(node.left, level - 1);
            printLevel(node.right, level - 1);
        }
    }

    void printLevels() {
        for (int i = 1; i <= height(); i++) {
            printLevel(root, i);
            // System.out.println();
        }
    }
    

    void printBreathFirst() {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node current = q.remove();
            System.out.print(current.value + " "); // operation
            if (current.left != null) {
                q.add(current.left);
            }
            if (current.right != null) {
                q.add(current.right);
            }
        }
    }

    void printPreOrder() {
        if (root == null) {
            return;
        }        
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            System.out.print(current.value + " ");
            if (current.right != null) 
                stack.push(current.right);
            if (current.left != null)
                stack.push(current.left);
        }
    }
    void printInOrder() {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            System.out.print(current.value + " ");
            current = current.right;
        }
    }
    void printPostOrder() {
        Stack<Node> stack = new Stack<>();
        Node node = root;
        while (true) {
            while (node != null) {
                stack.push(node);
                stack.push(node);
                node = node.left;
            }
            if (stack.isEmpty())
                return;
            node = stack.pop();
            if (!stack.isEmpty() && stack.peek() == node)
                node = node.right;
            else {
                System.out.print(node.value + " ");
                node = null;
            }
        }
    }    

    void insertLevelOrder(int value) {
        Node node = new Node(value);
        if (root == null) {
            root = node;
            return;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node current = q.remove();
            if (current.left == null) {
                current.left = node;
                break;
            }
            else
                q.add(current.left);
            if (current.right == null) {
                current.right = node;
                break;
            }
            else
                q.add(current.right);
        }
    }

    Node insertInBST(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insertInBST(node.left, value);
        }
        else {
            node.right = insertInBST(node.right, value);
        }
        return node;
    }

    Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    int findNext(int value) {
        return findNext(root, null, value).value;
    }

    Node findNext(Node node, Node next, int value) {
        if (node == null) {
            return null;
        }

        if (node.value == value) {
            if (node.right != null) {
                return findMin(node.right);
            }
        }
        else if (node.value > value) {
            next = node;
            return findNext(node.left, next, value);
        }
        else {
            return findNext(node.right, next, value);
        }
        return next;
    }

    // Breadth First Traversal
    void deleteDeepest(Node node) {
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        Node current = root;
        while (!q.isEmpty()) {
            current = q.remove();

            // Process node
            if (current == node) {
                current = null;
                return;
            }

            // Right subtree
            if (current.right != null) {
                if (current.right == node) {
                    current.right = null;
                    return;
                }
                else {
                    q.add(current.right);
                }
            }

            // Left subtree
            if (current.left != null) {
                if (current.left == node) {
                    current.left = null;
                    return;
                }
                else {
                    q.add(current.left);
                }
            }
        }
    }

    void delete(int value) {
        if (root == null) {
            return;
        }
        if (isLeaf(root)) {
            if (root.value == value) {
                return;
            }
            else {
                return;
            }
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        Node current = root, node = null;
        while (!q.isEmpty()) {
            current = q.remove();
            if (current.value == value) {
                node = current;
            }
            if (current.left != null) {
                q.add(current.left);
            }
            if (current.right != null) {
                q.add(current.right);
            }
        }

        if (node != null) {
            value = current.value;
            deleteDeepest(current);
            node.value = value;
        }
    }

    void preOrder() {
        preOrder(root);
    }

    void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // final int ASCII_MAX = 128;
    // int lengthOfLongestSubstring(String s) {

    //     // Naive approach
    //     // Loop over string, counting the occurance for each character
    //     // In this loop, we should check if our current character's occurance...
    //     // ..is greater than 1. If so, we return our current index in loop.
    //     // If loop completes, we return 1 because no repeating characters were found.

    //     int[] array = new int[ASCII_MAX];
    //     int i, record = 0, count = 0;
    //     for (i = 0; i < s.length(); i++) {
    //         array[s.charAt(i)]++;
    //         count++;
    //         if (array[s.charAt(i)] > 1) {
    //             if (record < count) {
    //                 record = count - 1;
    //             }
    //             count = 0;
    //         }
    //         else {
    //             if (record < count) {
    //                 record = count;
    //             }               
    //         }
    //     }
    //     return record;
        
        
    // }


    public static void main(String args[]) {
        BiTree2 go = new BiTree2();
        System.out.println(go.lengthOfLongestSubstring(" ss1224ghz"));
    }



    // public static void main(String args[]) {
    //     BiTree2 go = new BiTree2();
    //     go.root = new Node(10);
    //     go.root.left = new Node(11);
    //     go.root.left.left = new Node(7);
    //     go.root.right = new Node(9);
    //     go.root.right.left = new Node(15);
    //     go.root.right.right = new Node(8);
    //     // go.root = new Node(10);
    //     // go.root.left = new Node(8);
    //     // go.root.right = new Node(2);
    //     // go.root.left.left = new Node(3);
    //     // go.root.left.right = new Node(5);
    //     // go.root.right.left = new Node(2);
    //     // go.root = new Node(1);
    //     // go.root.left = new Node(2);
    //     // go.root.right = new Node(3);
    //     // go.root.left.left = new Node(4);
    //     // go.root.left.right = new Node(5);
    //     // go.root.right.left = new Node(6);
    //     // go.root.right.right = new Node(7);
    //     // go.printLevels();
    //     // System.out.println();
    //     // go.printBreathFirst();
    //     // System.out.println();
    //     // go.preOrder();
    //     // System.out.println();
    //     // go.printPreOrder();
    //     // System.out.println();
    //     go.printInOrder();
    //     System.out.println();
    //     // go.printPostOrder();
    //     go.printInOrder();
    //     System.out.println();

    // }
    
    
}

import java.util.*;
import java.io.*;
import java.util.stream.IntStream;

public class tree_height {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    class Node {
        int key;
        List<Node> children;
        int height;

        public Node(int key) {
            this.key = key;
            this.children = new ArrayList<>();
        }

        public void addChild(Node child) {
            children.add(child);
        }
    }

    public class TreeHeight {
        int n;
        int parent[];
        Node root;

        void read() throws IOException {
            FastScanner in = new FastScanner();
            n = in.nextInt();
            Node[] nodes = IntStream.range(0, n).mapToObj(i -> new Node(i)).toArray(Node[]::new);
            for (int i = 0; i < n; i++) {
                int parent = in.nextInt();
                if (parent == -1) {
                    root = nodes[i];
                } else {
                    nodes[parent].addChild(nodes[i]);
                }
            }
        }

        int computeHeight() {

            if (root == null)
                return 0;

            Stack<Node> stack = new Stack<>();
            root.height = 1;
            stack.push(root);

            int maxHeight = 1;
            while (stack.size() > 0) {
                Node current = stack.pop();
                if (current.children.isEmpty()) {
                    if (current.height > maxHeight)
                        maxHeight = current.height;
                } else {
                    for (int i = 0; i < current.children.size(); i++) {
                        current.children.get(i).height = current.height + 1;
                        stack.push(current.children.get(i));
                    }
                }
            }

            return maxHeight;
            //return computeHeightRecursive(root);
        }

        private int computeHeightRecursive(Node tree) {
            if (tree == null)
                return 0;
            int maxHeight = 0;
            for (int i = 0; i < tree.children.size(); i++) {
                int height = computeHeightRecursive(tree.children.get(i));
                if (height > maxHeight)
                    maxHeight = height;
            }
            return maxHeight + 1;
        }
    }

    static public void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            public void run() {
                try {
                    new tree_height().run();
                } catch (IOException e) {
                }
            }
        }, "1", 1 << 26).start();
    }

    public void run() throws IOException {
        TreeHeight tree = new TreeHeight();
        tree.read();
        System.out.println(tree.computeHeight());
    }
}

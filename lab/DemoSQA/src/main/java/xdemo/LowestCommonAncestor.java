package xdemo;

/**
 * LAB: use assert / exception to achieve preventive programming; make the program robust
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class LowestCommonAncestor {

    public TreeNode findLCA(TreeNode root, TreeNode p, TreeNode q) {
        // 如果根節點為空，則返回空
        if (root == null) {
            return null;
        }

        if (!isFound(root, p) || !isFound(root, q)) {
            return null;
        }

        // 如果 p 和 q 都比 root 小，則最小公共祖先在左子樹
        if (p.val < root.val && q.val < root.val) {
            return findLCA(root.left, p, q);
        }

        // 如果 p 和 q 都比 root 大，則最小公共祖先在右子樹
        if (p.val > root.val && q.val > root.val) {
            return findLCA(root.right, p, q);
        }

        // 如果 p 和 q 分別位於 root 的左右兩側，則 root 是最小公共祖先
        return root;
    }

    public static void main(String[] args) {
        // 建立二叉搜尋樹
        TreeNode root = new TreeNode(6);
        root.left = new TreeNode(2);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.right.left = new TreeNode(3);
        root.left.right.right = new TreeNode(5);

        LowestCommonAncestor lca = new LowestCommonAncestor();

        TreeNode a = root.left;
        TreeNode b = root.right;
        TreeNode ancestor = lca.findLCA(root, a, b);
        System.out.printf("\nThe LCA of node %d and node %d is: %d", a.val, b.val, ancestor.val);

        a = root.left.right.left;
        b = root.left.right.right;
        ancestor = lca.findLCA(root, a, b);
        System.out.printf("\nThe LCA of node %d and node %d is: %d", a.val, b.val, ancestor.val);

        a = root.left.right.left;
        b = new TreeNode(10);
        ancestor = lca.findLCA(root, a, b);
        String result = (ancestor == null) ? "null" : String.valueOf(ancestor.val);
        System.out.printf("\nThe LCA of node %d and node %d is: %s", a.val, b.val, result);
    }

    public boolean isFound(TreeNode root, TreeNode node) {
        if (root == null)
            return false;
        if (node.val > root.val) {
            return isFound(root.right, node);
        } else if (node.val < root.val) {
            return isFound(root.left, node);
        } else
            return true;
    }
}

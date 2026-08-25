package xdemo;

/**
 * Fix the bug of this program
 */

class TreeNode {
    protected int val;
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
        System.out.printf("The LCA of node %d and node %d is: %d", a.val, b.val, ancestor.val);

//        TreeNode a = root.left.right.left;
//        TreeNode b = root.left.right.right;
//        TreeNode ancestor = lca.findLCA(root, a, b);
//        System.out.printf("The LCA of node %d and node %d is: %d", a.val, b.val, ancestor.val);
    }
}

import org.junit.Test;

import java.util.*;

public class BasicJTests {
    @Test
    public void testHello() {
        System.out.println("hello");
    }

    @Test
    public void testLevelTraverseOrder() {
        TreeNode treeNode = new TreeNode();
        treeNode.val = 1;
        TreeNode treeNode2 = new TreeNode();
        treeNode2.val = 2;
        TreeNode treeNode3 = new TreeNode();
        treeNode3.val = 3;
        TreeNode treeNode4 = new TreeNode();
        treeNode4.val = 4;
        treeNode.left = treeNode2;
        treeNode.right = treeNode3;
        treeNode2.right = treeNode4;

        List<List<Integer>> lists = levelOrderByQueue(treeNode);
        System.out.println(lists);
    }

    public List<List<Integer>> levelOrderByQueue(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> ret = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<TreeNode> levelNodes = new ArrayList<>();
            while (queue.peek() != null) {
                levelNodes.add(queue.poll());
            }
            List<Integer> printLevel = new ArrayList<>();
            for (TreeNode node : levelNodes) {
                printLevel.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            ret.add(printLevel);
        }
        return ret;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();

        List<TreeNode> list = new ArrayList<>();
        list.add(root);

        while (list.size() > 0) {
            List<Integer> print = new ArrayList<>();
            List<TreeNode> next = new ArrayList<>();
            for (TreeNode node : list) {
                if (node == null) {
                    continue;
                }
                print.add(node.val);
                if (node.left != null) {
                    next.add(node.left);
                }
                if (node.right != null) {
                    next.add(node.right);
                }
            }
            ret.add(print);
            list = next;
        }

        return ret;
    }

    @Test
    public void testLastRemaining() {
        System.out.println(lastRemaining(5, 3));
    }

    //约瑟夫环
    public int lastRemaining(int n, int m) {
        if (n <= 0) {
            return 0;
        }
        List<Integer> list = new ArrayList<>();
        int i = 0;
        while (i < n) {
            list.add(i);
            i++;
        }
        int inx = 0;
        while (n > 1) {
            inx = (inx + m - 1) % n;
            list.remove(inx);
            n--;
        }
        return list.get(0);
    }

    @Test
    public void testRob() {
        System.out.println(rob(new int[]{1, 2, 3, 1}));
        System.out.println(rob(new int[]{2, 1, 1, 2}));
        System.out.println(rob(new int[]{2, 7, 9, 3}));
        System.out.println(rob(new int[]{2, 1}));
    }

    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int max = nums[0];
        Map<Integer, Integer> dp = new HashMap<>();
        dp.put(0, nums[0]);

        for (int i = 1; i < nums.length; i++) {
            int searchInx = i - 2;
            int maxBefore = 0;
            while (searchInx >= 0) {
                maxBefore = Math.max(maxBefore, dp.get(searchInx));
                searchInx--;
            }
            int sum = maxBefore + nums[i];
            max = Math.max(max, sum);
            dp.put(i, sum);
        }
        return max;
    }

    @Test
    public void testReversePairs() {
        System.out.println(reversePairs(new int[]{3, 5, 2, 2}));
    }

    public int reversePairs(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    @Test
    public void testThousandSeparator() {
        System.out.println(thousandSeparator(12345));
        System.out.println(thousandSeparator(123455678));
    }

    public String thousandSeparator(int n) {
        String numStr = String.valueOf(n);
        List<Character> tmp = new ArrayList<>();
        for (int i = numStr.length() - 1; i >= 0; i--) {
            tmp.add(numStr.charAt(i));
            if (i > 0 && (numStr.length() - i) % 3 == 0) {
                tmp.add('.');
            }
        }
        String ret = "";
        for (int i = tmp.size() - 1; i >= 0; i--) {
            ret = ret + tmp.get(i);
        }
        return ret;
    }

    @Test
    public void testRemoveDuplicateLetters() {
        System.out.println(removeDuplicateLetters("bcabc"));
        System.out.println(removeDuplicateLetters("cbacdcbc"));
        System.out.println(removeDuplicateLetters("ecbacba"));
    }

    public String removeDuplicateLetters(String s) {
        int[] list = new int[26];
        for (int i = 0; i < s.length(); i++) {
            list[s.charAt(i) - 'a']++;
        }
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            list[c - 'a']--;
            if (deque.contains(c)) {
                continue;
            }
            while (deque.peekLast() != null && deque.peekLast() > c) {
                //如果栈顶元素比当前元素序列大
                //那么判断栈顶元素在之后还能否出现
                //如果能出现则弹出栈顶元素，舍弃
                if (list[deque.peekLast() - 'a'] > 0) {
                    deque.removeLast();
                } else {
                    break;
                }
            }
            deque.addLast(c);
        }
        StringBuilder builder = new StringBuilder();
        while (!deque.isEmpty()) {
            builder.append(deque.removeFirst());
        }
        return builder.toString();
    }

    ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        Set<ListNode> set = new HashSet<>();
        while (headA != null) {
            set.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (set.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }
        return null;
    }

    @Test
    public void testMerge() {
        int[] a = new int[]{1, 2, 3, 0, 0, 0};
        merge(a, 3, new int[]{2, 5, 6}, 3);
        System.out.println(Arrays.toString(a));
    }

    public void merge(int[] A, int m, int[] B, int n) {
        List<Integer> tmp = new ArrayList<>();
        int i = 0, j = 0;
        while (i < m && j < n) {
            if (A[i] <= B[j]) {
                tmp.add(A[i]);
                i++;
            } else {
                tmp.add(B[j]);
                j++;
            }
        }
        while (i < m) {
            tmp.add(A[i]);
            i++;
        }
        while (j < n) {
            tmp.add(B[j]);
            j++;
        }
        int[] tmpArray = tmp.stream().mapToInt(Integer::intValue).toArray();
        System.arraycopy(tmpArray, 0, A, 0, A.length);
    }

    @Test
    public void testMultiply() {
        System.out.println(multiply("2", "3"));
        System.out.println(multiply("123456789", "987654321"));
        System.out.println(multiply("52", "0"));
        System.out.println(multiply("881095803", "61"));
    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        String ans = "0";
        int m = num1.length(), n = num2.length();
        for (int i = n - 1; i >= 0; i--) {
            StringBuffer buffer = new StringBuffer();
            int add = 0;
            for (int j = n - 1; j > i; j--) {
                buffer.append(0);
            }
            int y = num2.charAt(i) - '0';
            for (int j = m - 1; j >= 0; j--) {
                int x = num1.charAt(j) - '0';
                int product = x * y + add;
                buffer.append(product % 10);
                add = product / 10;
            }
            if (add != 0) {
                buffer.append(add % 10);
            }
            ans = addStringValue(ans, buffer.reverse().toString());
        }
        return ans;
    }

    @Test
    public void testAddStringValue() {
        System.out.println(addStringValue("123456789", "0"));
    }

    private String addStringValue(String value1, String value2) {
        if (value1 == null || value1.length() == 0 || (value1.length() == 1 && value1.charAt(0) == '0')) {
            return value2;
        }
        if (value2 == null || value2.length() == 0 || (value2.length() == 1 && value2.charAt(0) == '0')) {
            return value1;
        }
        int diff = value1.length() - value2.length();
        if (diff > 0) {
            while (diff > 0) {
                value2 = '0' + value2;
                diff--;
            }
        } else if (diff < 0) {
            while (diff < 0) {
                value1 = '0' + value1;
                diff++;
            }
        }

        StringBuilder ret = new StringBuilder();
        int upgrade = 0;
        for (int i = value1.length() - 1; i >= 0; i--) {
            int num1 = Integer.parseInt(value1.substring(i, i + 1));
            int num2 = Integer.parseInt(value2.substring(i, i + 1));
            int sum = num1 + num2 + upgrade;
            ret.append(sum % 10);
            upgrade = sum / 10;
        }
        if (upgrade > 0) {
            ret.append(upgrade);
        }
        return ret.reverse().toString();
    }
}


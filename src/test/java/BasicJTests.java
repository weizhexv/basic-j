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

    @Test
    public void testCharNum() {
        char a = 'a';
        char A = 'A';
        char z = 'z';
        System.out.println((int) a);
        System.out.println((int) A);
        System.out.println((int) z);
    }

    @Test
    public void testAddTwoStrings() {
        System.out.println(addStrings("11", "123"));
        System.out.println(addStrings("456", "77"));
        System.out.println(addStrings("0", "0"));
    }

    public String addStrings(String num1, String num2) {
        if (num1 == null) {
            return num2;
        }
        if (num2 == null) {
            return num1;
        }
        int maxLen = Math.max(num1.length(), num2.length());
        if (num1.length() > num2.length()) {
            int diff = num1.length() - num2.length();
            while (diff > 0) {
                num2 = '0' + num2;
                diff--;
            }
        } else if (num2.length() > num1.length()) {
            int diff = num2.length() - num1.length();
            while (diff > 0) {
                num1 = '0' + num1;
                diff--;
            }
        }
        String ret = "";
        int upgrade = 0;
        for (int i = maxLen - 1; i >= 0; i--) {
            int val1 = Integer.parseInt(String.valueOf(num1.charAt(i)));
            int val2 = Integer.parseInt(String.valueOf(num2.charAt(i)));
            int sum = val1 + val2 + upgrade;
            upgrade = sum / 10;
            sum = sum % 10;
            ret = sum + ret;
        }

        if (upgrade > 0) {
            ret = upgrade + ret;
        }
        return ret;
    }

    @Test
    public void testSpiralOrder() {
        System.out.println(spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
        System.out.println(spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}}));
    }

    public int[] spiralOrder(int[][] matrix) {
        List<Integer> ret = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return ret.stream().mapToInt(Integer::intValue).toArray();
        }

        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;

        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) {
                ret.add(matrix[top][i]);
            }
            top++;
            for (int j = top; j <= bottom; j++) {
                ret.add(matrix[j][right]);
            }
            right--;
            if (left <= right && top <= bottom) {
                for (int i = right; i >= left; i--) {
                    ret.add(matrix[bottom][i]);
                }
                bottom--;
                for (int j = bottom; j >= top; j--) {
                    ret.add(matrix[j][left]);
                }
                left++;
            }
        }
        return ret.stream().mapToInt(Integer::intValue).toArray();
    }

    @Test
    public void testIsPalindrome() {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(isPalindrome("race a car"));
        System.out.println(isPalindrome("0P"));
    }

    public boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        if (s.length() == 1) {
            return true;
        }

        s = s.toLowerCase();

        int len = s.length();
        int i = 0;
        int j = len - 1;
        while (j >= i) {
            char charI = s.charAt(i);
            if (!isAlphabetOrNumeric(charI)) {
                i++;
                continue;
            }
            char charJ = s.charAt(j);
            if (!isAlphabetOrNumeric(charJ)) {
                j--;
                continue;
            }
            if (s.charAt(j) != s.charAt(i)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    private boolean isAlphabetOrNumeric(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        return false;
    }

    @Test
    public void testLevelZOrder() {
        TreeNode root = new TreeNode();
        root.val = 3;
        TreeNode node1 = new TreeNode();
        node1.val = 9;
        TreeNode node2 = new TreeNode();
        node2.val = 20;
        TreeNode node3 = new TreeNode();
        node3.val = 15;
        TreeNode node4 = new TreeNode();
        node4.val = 7;

        root.left = node1;
        root.right = node2;
        node2.left = node3;
        node2.right = node4;

        System.out.println(levelZOrder(root));
    }

    public List<List<Integer>> levelZOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        boolean asc = true;
        while (!queue.isEmpty()) {
            Deque<Integer> level = new LinkedList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode treeNode = queue.poll();
                if (asc) {
                    level.addLast(treeNode.val);
                } else {
                    level.addFirst(treeNode.val);
                }
                if (treeNode.left != null) {
                    queue.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.add(treeNode.right);
                }
            }
            ret.add((List<Integer>) level);
            asc = !asc;
        }

        return ret;
    }

    @Test
    public void testFindCommonLength() {
        System.out.println(findCommonLength(new int[]{1, 2, 3, 2, 1}, new int[]{3, 2, 1, 4, 7}));
        System.out.println(findCommonLength(new int[]{0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}));
    }

    interface IncrementClosure {
        void incr(int i);
    }

    @Test
    public void testClosureFunc() {
        IncrementClosure func = (i) -> {
            i = i + 1;
        };
        int num = 0;

        func.incr(num);
        System.out.println(num);
        func.incr(num);
        System.out.println(num);

    }

    interface IDpFunc {
        int getFromDp(int i, int j);
    }

    public int findCommonLength(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return 0;
        }
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[][] dp = new int[len1][len2];

        IDpFunc func = (i, j) -> {
            if (i < 0) {
                return 0;
            }
            if (j < 0) {
                return 0;
            }
            return dp[i][j];
        };

        int maxLen = 0;
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (nums1[i] == nums2[j]) {
                    int preLen = func.getFromDp(i - 1, j - 1);
                    int len = 1 + preLen;
                    dp[i][j] = len;
                    maxLen = Math.max(maxLen, len);
                }
            }
        }

        return maxLen;
    }

    @Test
    public void testLongestCommonSubsequence() {
        System.out.println(longestCommonSubsequence("abced", "ace"));
        System.out.println(longestCommonSubsequence("abc", "abc"));
        System.out.println(longestCommonSubsequence("abc", "def"));
        System.out.println(longestCommonSubsequence("abcde", "ace"));
    }

    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        int maxLen = 0;

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                char c1 = text1.charAt(i - 1);
                char c2 = text2.charAt(j - 1);

                int len;
                if (c1 == c2) {
                    len = dp[i - 1][j - 1] + 1;
                } else {
                    len = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
                dp[i][j] = len;
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }

    @Test
    public void testPermute() {
        System.out.println(permute(new int[]{1, 2, 3}));
        System.out.println(permute(new int[]{0, 1}));
        System.out.println(permute(new int[]{1}));
    }

    //全排列，用交换的方式节省空间
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return ret;
        }
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        doPermute(list, 0, ret);
        return ret;
    }

    private void doPermute(List<Integer> list, int pivot, List<List<Integer>> ret) {
        int len = list.size();
        if (len - 1 == pivot) {
            ret.add(list);
            return;
        }
        for (int i = pivot; i < len; i++) {
            List<Integer> tmp = new ArrayList<>(list);
            Collections.swap(tmp, pivot, i);
            doPermute(tmp, pivot + 1, ret);
        }
    }

    @Test
    public void testExchangeOddsBeforeEvent() {
        System.out.println(Arrays.toString(exchangeOddsBeforeEvens(new int[]{1, 2, 3, 4})));
    }

    public int[] exchangeOddsBeforeEvens(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        int len = nums.length;
        int i = 0;
        int j = len - 1;
        while (j >= i) {
            int x = nums[i];
            int y = nums[j];
            if (x % 2 == 1) {
                //odd
                i++;
                continue;
            }
            if (y % 2 == 0) {
                //even
                j--;
                continue;
            }
            //swap, this part must x even, y odd
            nums[i] = y;
            nums[j] = x;
            i++;
            j--;
        }
        return nums;
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        //infer map & len
        Map<Integer, ListNode> map = new HashMap<>();
        int len = 0;
        while (head != null) {
            map.put(len, head);
            head = head.next;
            len++;
        }
        return map.get(len - k);
    }

    public ListNode getKthFromEndV2(ListNode head, int k) {
        int len = 0;
        ListNode node = head;
        while (node != null) {
            node = node.next;
            len++;
        }
        int inx = len - k + 1;
        while (inx > 1) {
            head = head.next;
            inx--;
        }
        return head;
    }

    //删除重复的元素
    public ListNode deleteDuplicates(ListNode head) {
        Set<Integer> mem = new HashSet<>();
        mem.add(head.val);

        ListNode preNode = head;
        ListNode currentNode = head.next;
        while (currentNode != null) {
            if (mem.contains(currentNode.val)) {
                preNode.next = currentNode.next;
                currentNode = currentNode.next;
            } else {
                mem.add(currentNode.val);
                preNode = currentNode;
                currentNode = currentNode.next;
            }
        }
        return head;
    }
}


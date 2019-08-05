package com.daily.algorithm;

public class Algorithm190805 {

//    You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
//    Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
//    Output: 7 -> 0 -> 8

//  342 + 465 = 807 -> 708
// {1,8} {0}  81 + 0 = 81 ->18
//{9},{1,9,9,9,9,9,9,9,9,9}    -> 9 + 9999999991   注意长度
    // test-case

    //first-mine
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        long l = Long.valueOf(rsum(l1));
        long r = Long.valueOf(rsum(l2));
        return proListNode(String.valueOf(l + r), new ListNode(0), String.valueOf(l + r).length());
    }

    private static String rsum(ListNode l1) {
        if (l1.next == null){
            return l1.val + "";
        }
        return  rsum(l1.next) + l1.val;
    }

    public static ListNode proListNode(String charArr, ListNode ll, int index){
        ll.val = Integer.valueOf(charArr.substring(index - 1, index));
        if (index <= 1){
            ll.next = null;
            return ll;
        }
        ll.next = proListNode(charArr, new ListNode(0), --index);
        return ll;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(5);
//        l1.next = new ListNode(4);
//        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
//        l2.next = new ListNode(6);
//        l2.next.next = new ListNode(4);
        ListNode aa = addTwoNumbers(l1,l2);

        System.out.println(aa);
    }

    /**
     * 其实不用考虑别的，就是两个数字相加，和传统加法唯一的不同就是此题中的加法是从左往右算的，进位也是从左往右进。
     * 例子里给的就是
     *      2  4  3
     * ＋   5  6  4
     * ——————
     *     7  0  8
     * 正常加法应该先算3+4， 接着4+6，进一位，最后2+5，加之前的进位1，得到8；
     * 在本题就应该先算 2+5， 接着4+6，进一位到3+4中，3+4+1=8，最后得到708。
     * 对于两个list不等长的情况，比如1->8 + 0，就把短的list的末尾用0补齐就行了。
     * 所以我直接遍历两个链表了，取出来的数相加放到新表里。当l1.next或者l2.next == null了，用0替代l1.val或l2.val。
     * 最后还要注意当两个链表都遍历完，相加完之后，如果还有进位，要把进位的数字放到新list的末尾。比如 5 + 5  = 0->1
     */
    //other-people
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        int plus = 0;
        ListNode pl1 = l1;
        ListNode pl2 = l2;
        ListNode head = null;
        ListNode cur = null;
        while(plus != 3){
            if(pl1 == null&& pl2 == null){
                cur.next = plus==0?null:new ListNode(plus);
                break;
            }
            int num1 = (pl1==null)?0:pl1.val;
            int num2 = (pl2==null)?0:pl2.val;
            int sum = num1 + num2 + plus;
            plus = sum>9?1:0;
            sum = sum%10;
            if(cur == null){
                head = new ListNode(sum);
                cur = head;
            }else{
                cur.next = new ListNode(sum);
                cur = cur.next;
            }
            pl1 = (pl1==null)?pl1:pl1.next;
            pl2 = (pl2==null)?pl2:pl2.next;
        }
        return head;
    }


}


class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}

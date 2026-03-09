class Solution {
    public ListNode swapPairs(ListNode head) {
      if (head == null || head.next == null) 
          return head;
        ListNode new_head = head.next;
        ListNode temp = head.next.next;
        new_head.next = head;
        head.next = swapPairs(temp);
        return new_head;
    }
}
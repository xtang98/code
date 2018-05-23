package algorithms.list;
class ListNode {
	int val;
	ListNode next;
	public ListNode(int v) {
		val = v;
	}
}
public class LinkedList {

	/**
	 * sort a linked list in O(nlgn), using a variation of merge sort
	 * @param head
	 */
	public static ListNode sortList(ListNode head) {
		//termination condition
		if (head == null || head.next == null) return head;
		//now at least two nodes, find the "middle" guy
		ListNode pre=null, slow = head, fast = head;
		while (fast != null) {
			pre = slow;
			slow = slow.next;
			fast = fast.next;
			if (fast != null) fast = fast.next;
		}
		pre.next = null;
		head = sortList(head);
		slow = sortList(slow);
		//now merge the two list
		return mergeList(head, slow);
	}
	public static ListNode mergeList(ListNode a, ListNode b) {
		ListNode dummy = new ListNode(-1), cur=dummy;
		while (a!=null && b!= null) {
			if (a.val<b.val) {
				cur.next = a; a=a.next; 
			} else {
				cur.next = b; b=b.next;
			}
			cur = cur.next;
		}
		if (a!=null) cur.next = a;
		if (b!=null) cur.next = b;
		return dummy.next;
	}
	
	public static void printList(ListNode head) {
		while (head != null) {
			System.out.print(head.val+"-->");
			head = head.next;
		}
		System.out.println("null");
	}
	public static void main(String[] args) {
		//sort 4->2->3->1
		ListNode head = new ListNode(4);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(1);
		head = sortList(head);
		printList(head);

	}

}

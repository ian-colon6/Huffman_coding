package SortedList;


/**
 * Implementation of a SortedList using a SinglyLinkedList
 * @author Fernando J. Bermudez & Juan O. Lopez
 * @author Ián G. Colón Rosado
 * @version 2.0
 * @since 10/16/2021
 */
public class SortedLinkedList<E extends Comparable<? super E>> extends AbstractSortedList<E> {

	@SuppressWarnings("unused")
	private static class Node<E> {

		private E value;
		private Node<E> next;

		public Node(E value, Node<E> next) {
			this.value = value;
			this.next = next;
		}

		public Node(E value) {
			this(value, null); // Delegate to other constructor
		}

		public Node() {
			this(null, null); // Delegate to other constructor
		}

		public E getValue() {
			return value;
		}

		public void setValue(E value) {
			this.value = value;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setNext(Node<E> next) {
			this.next = next;
		}

		public void clear() {
			value = null;
			next = null;
		}				
	} // End of Node class

	
	private Node<E> head; // First DATA node (This is NOT a dummy header node)
	
	public SortedLinkedList() {
		head = null;
		currentSize = 0;
	}

	@Override
	public void add(E e) {
		/* TODO ADD CODE HERE */
		/* Special case: Be careful when the new value is the smallest */
		Node<E> newNode = new Node<>(e);
		Node<E> curNode;

		/* 
		 * If the list is empty or newNode is the smallest
		 * replace newNode as the new head 
		 */
		if(this.head == null || newNode.getValue().compareTo(this.head.getValue()) < 0){
			
			newNode.setNext(this.head);
			this.head = newNode;
			
		}else{
			//Initialize curNode as head if not empty
			curNode = this.head;
			//Continue to iterate through nodes until a larger value is found
			// or until curNode reaches the last element
			while(curNode.getNext() != null && newNode.getValue().compareTo(curNode.getNext().getValue()) > 0) {
	
				curNode = curNode.getNext();
			}
			//Replace newNode's larger value to point to curNode's next
			//Make curNode point to newNode
			newNode.setNext(curNode.getNext());
			curNode.setNext(newNode);
			
		}
		
		this.currentSize++;

	}

	@Override
	public boolean remove(E e) {
		/* TODO ADD CODE HERE */
		/* Special case: Be careful when the value is found at the head node */
		Node<E> rmNode, curNode;
		/* Initialize curNode as head */
		curNode = this.head;
		while(curNode != null){

			/* 
				If curNode's element is equal to the one to be removed:
				assign curNode to rmNode
				assign curNode to its next element
				nullify rmNode to remove from list and return true
			*/
			if(curNode.getValue().equals(e)){
				rmNode = curNode;
				curNode = curNode.getNext();
				rmNode.clear();
				this.currentSize--;
				return true;
			}else{
				curNode = curNode.getNext();
			}
		}
		
		return false;
	}

	@Override
	public E removeIndex(int index) {
		/* TODO ADD CODE HERE */
		/* Special case: Be careful when index = 0 */
		Node<E> rmNode, curNode;
		E value = null;

		
		if(index == 0) {
			rmNode = this.head;
			this.head = this.head.getNext();
		}
		
		else {
			curNode = this.head;
			for(int i = 0; i < index - 1; i++) {
				curNode = curNode.getNext();
			}

			rmNode = curNode.getNext();
			curNode.setNext(rmNode.getNext());
			
		}
		value = rmNode.getValue();
		rmNode.clear();
		this.currentSize--;
		
		return value;
	}

	@Override
	public int firstIndex(E e) {
		/* TODO ADD CODE HERE */
		int target = 0;
		
		/**
		 * Initialize curr as head of list
		 * when the value of curr is equal to e
		 * it returns the target index
		 * else keep iterating through list and increasing target
		 * if not found returns -1
		 */
		Node<E> curr = head;
		while(curr != null){
			if(curr.getValue().equals(e)){
				return target;
			}else{
				curr = curr.getNext();
				target++;
			}

			if(curr.getNext() == null){
				return -1;
			}
		}
		
		return target;
		
	}

	@Override
	public E get(int index) {
		/* TODO ADD CODE HERE */
		
		/**
		 * Initialize target as head of list
		 */
		Node<E> target = this.head; 

		/**
		 * Assign the node that is reached when i equals index to target and return it
		 */
		for (int i = 0; i < index; i++){
			target = target.getNext(); 
		}

		return target.getValue();
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray() {
		int index = 0;
		E[] theArray = (E[]) new Comparable[size()]; // Cannot use Object here
		for(Node<E> curNode = this.head; index < size() && curNode  != null; curNode = curNode.getNext(), index++) {
			theArray[index] = curNode.getValue();
		}
		return theArray;
	}

}

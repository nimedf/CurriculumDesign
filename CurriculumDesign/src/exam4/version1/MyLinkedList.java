package exam4.version1;

public class MyLinkedList<E> extends MyAbstractList<E> {
	private Node<E> head, tail;

	public MyLinkedList() {
	}
	
	public MyLinkedList(E[] objects) {
		super(objects);
	}
	
	/**
	 * 返回链表里面的第一个元素
	 * @return E
	 * */
	public E getFirst() {
		if (size == 0)
			return null;
		else {
			return head.element;
		}
	}
	
	/**
	 * 返回最后一个元素
	 * @return E
	 * */
	public E getLast() {
		if (size == 0)
			return null;
		else
			return tail.element;
	}
	
	/**
	 * 在指定的下标处添加指定元素
	 * @param index
	 * @param e
	 * @return void
	 * */
	public void add(int index, E e) {
		isIndexRight(index);
		
		if (index == 0)
			addFirst(e);
		else if (index == size)
			addLast(e);
		else {
			Node<E> current = head;
			
			for (int i = 1; i < index; i++)
				current = current.next;
			
			Node<E> newNode = new Node<E>(e);
			newNode.next = current.next;
			current.next = newNode;
			size++;
		}
	}
	
	/**
	 * 在链表的第一个位置添加一个元素
	 * @param e
	 * @return void
	 * */
	public void addFirst(E e) {
		Node<E> newNode = new Node<E>(e);
		newNode.next = head;
		head = newNode;
		size++;
		// 如果新添加的这个节点是这个链表里面唯一的一个节点
		//就把尾指针指向这个节点
		if (tail == null) 
			tail = head;
	}
	
	/**
	 * 在链表的末尾添加一个元素
	 * @param e
	 * @return void
	 * */
	public void addLast(E e) {
		Node<E> newNode = new Node<E>(e);
		
		if (tail == null) {	// 表明链表是空链表
			head = tail = newNode;
		}
		else {
			tail.next = newNode;
			tail = newNode;	// 把尾指针移动到最后
			// tail = tail.next;
		}
		
		size++;
	}

	/**
	 * 将链表清空
	 * @return void
	 * */
	public void clear() {
		size = 0;
		head = tail = null;
		
	}
	
	/**
	 * 删除链表的第一个元素
	 * @return E
	 * */
	public E removeFirst() {
		// 如果是空链表就返回null值
		if (size == 0)
			return null;
		else if (size == 1) {	// 如果链表里面只有一个元素
			Node<E> temp = head;	// 保存要删除节点的地址
			clear();	// 清空链表
			return temp.element;
		}
		else {	// 表明链表里面不止一个元素
			E temp = head.element;	// 保存要删除节点的值
			head = head.next;	// 删除第一个节点
			size--;	// 对应的size减一
			return temp;
		}
	}

	/**
	 * 
	 * */
	public E removeLast() {
		if (size == 0)	// 表明链表是空链表
			return null;
		else if(size == 1) {	// 链表里面只有一个元素
			Node<E> temp = head;	// 保存要删除的节点的地址
			clear();	// 清空链表
			return temp.element;
		}
		else {		// 链表里面不止一个元素，至少有两个元素及其以上
			Node<E> current = head;
			for (int i = 1; i < size - 2; i++)	// 移动指针到要删除元素的前一个
				current = current.next;
			Node<E> temp = tail;	// 保存要删除元素的地址
			tail = current;		//删除元素
			tail.next = null;
			size--;
			
			return temp.element;
		}
	}
	
	/**
	 * 删除指定下标处的元素
	 * @param index
	 * @return E
	 * */
	public E remove(int index) {
		isIndexRight(index);
		
		if (index == 0)	//	删除第一个元素
			return removeFirst();
		else if (index == size)	// 删除最后一个元素
			return removeLast();
		else {	// 删除中间元素
			Node<E> current = head;
			for (int i = 1; i < index; i++)	//移动指针到要删除元素的前一个
				current = current.next;
			Node<E> temp = current.next;	// 保存将删除的元素的地址
			current.next = current.next.next;	// 删除元素
			size--;
			return temp.element;
		}
	}

	/**
	 * 判断一个元素是否在链表中
	 * @param e	判断元素
	 * @return true 所判断元素在链表里面
	 * @return false 所判断元素没在链表里面
	 * */
	public boolean contain(E e) {
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			// 如果链表里面包含这个元素，就返回true
			if (current.element.equals(e))
				return true;
			
			current = current.next;
		}
		
		return false;
	}

	/**
	 * 返回指定下标的元素
	 * @param index 指定的下标
	 * @return E 
	 * */
	public E get(int index) {
		// 如果下标不合格就返回null
		if (index < 0 || index >= size)
			return null;
		else if (index == 0)	// 如果下标为0，就返回第一个元素
			return getFirst();
		else if (index == size - 1)	// 如果下标为size-1，就返回最后一个元素
			return getLast();
		else {	// 返回链表中的元素
			Node<E> current = head;
			for (int i = 0; i < index; i++)
				current = current.next;
			
			return current.element;
		}
	}

	/**
	 * 返回指定元素的下标
	 * @param e 指定的元素
	 * @return -1 链表里面没有所指定的元素
	 * @return >0 所指定元素的下标
	 * */
	public int indexOf(E e) {
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			if (e.equals(current.element))
				return i;
			
			current = current.next;
		}
		
		return -1;
	}

	/**
	 * 返回最后一个与指定元素匹配的下标
	 * @param e	指定的元素
	 * @return -1 链表里面没有这个元素
	 * @return >0 
	 * */
	public int lastIndexOf(E e) {
		int index = -1;
		
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			if (e.equals(current.element))
				index = i;
			
			current = current.next;
		}
		
		return index;
	}

	/**
	 * 用指定的元素将指定下标的元素代替
	 * @param index 指定下标
	 * */
	public void set(int index, E e) {
		if (index == 0){
			head.element = e;
		}
		else {
			Node<E> current = head;
			for (int i = 1; i < index; i++) {
				current = current.next;
			}
			
			(current.next).element = e;
		}
	}
	
	/**
	 * 检测下标是否合法
	 * */
	private void isIndexRight(int index) {
		if (index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException("下标不合法");
		}
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			result.append(current.element);
			current = current.next;
			if (current != null)
				result.append(", ");
			else 
				result.append("]");
		}
		
		return result.toString();
	}

	@SuppressWarnings("hiding")
	class Node<E> {
		E element;
		Node<E> next;
		
		public Node(E e) {
			this.element = e;
		}
	}
}

package exam4.version1;

public class MyLinkedList<E> extends MyAbstractList<E> {
	private Node<E> head, tail;

	public MyLinkedList() {
	}
	
	public MyLinkedList(E[] objects) {
		super(objects);
	}
	
	/**
	 * ������������ĵ�һ��Ԫ��
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
	 * �������һ��Ԫ��
	 * @return E
	 * */
	public E getLast() {
		if (size == 0)
			return null;
		else
			return tail.element;
	}
	
	/**
	 * ��ָ�����±괦���ָ��Ԫ��
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
	 * ������ĵ�һ��λ�����һ��Ԫ��
	 * @param e
	 * @return void
	 * */
	public void addFirst(E e) {
		Node<E> newNode = new Node<E>(e);
		newNode.next = head;
		head = newNode;
		size++;
		// �������ӵ�����ڵ��������������Ψһ��һ���ڵ�
		//�Ͱ�βָ��ָ������ڵ�
		if (tail == null) 
			tail = head;
	}
	
	/**
	 * �������ĩβ���һ��Ԫ��
	 * @param e
	 * @return void
	 * */
	public void addLast(E e) {
		Node<E> newNode = new Node<E>(e);
		
		if (tail == null) {	// ���������ǿ�����
			head = tail = newNode;
		}
		else {
			tail.next = newNode;
			tail = newNode;	// ��βָ���ƶ������
			// tail = tail.next;
		}
		
		size++;
	}

	/**
	 * ���������
	 * @return void
	 * */
	public void clear() {
		size = 0;
		head = tail = null;
		
	}
	
	/**
	 * ɾ������ĵ�һ��Ԫ��
	 * @return E
	 * */
	public E removeFirst() {
		// ����ǿ�����ͷ���nullֵ
		if (size == 0)
			return null;
		else if (size == 1) {	// �����������ֻ��һ��Ԫ��
			Node<E> temp = head;	// ����Ҫɾ���ڵ�ĵ�ַ
			clear();	// �������
			return temp.element;
		}
		else {	// �����������治ֹһ��Ԫ��
			E temp = head.element;	// ����Ҫɾ���ڵ��ֵ
			head = head.next;	// ɾ����һ���ڵ�
			size--;	// ��Ӧ��size��һ
			return temp;
		}
	}

	/**
	 * 
	 * */
	public E removeLast() {
		if (size == 0)	// ���������ǿ�����
			return null;
		else if(size == 1) {	// ��������ֻ��һ��Ԫ��
			Node<E> temp = head;	// ����Ҫɾ���Ľڵ�ĵ�ַ
			clear();	// �������
			return temp.element;
		}
		else {		// �������治ֹһ��Ԫ�أ�����������Ԫ�ؼ�������
			Node<E> current = head;
			for (int i = 1; i < size - 2; i++)	// �ƶ�ָ�뵽Ҫɾ��Ԫ�ص�ǰһ��
				current = current.next;
			Node<E> temp = tail;	// ����Ҫɾ��Ԫ�صĵ�ַ
			tail = current;		//ɾ��Ԫ��
			tail.next = null;
			size--;
			
			return temp.element;
		}
	}
	
	/**
	 * ɾ��ָ���±괦��Ԫ��
	 * @param index
	 * @return E
	 * */
	public E remove(int index) {
		isIndexRight(index);
		
		if (index == 0)	//	ɾ����һ��Ԫ��
			return removeFirst();
		else if (index == size)	// ɾ�����һ��Ԫ��
			return removeLast();
		else {	// ɾ���м�Ԫ��
			Node<E> current = head;
			for (int i = 1; i < index; i++)	//�ƶ�ָ�뵽Ҫɾ��Ԫ�ص�ǰһ��
				current = current.next;
			Node<E> temp = current.next;	// ���潫ɾ����Ԫ�صĵ�ַ
			current.next = current.next.next;	// ɾ��Ԫ��
			size--;
			return temp.element;
		}
	}

	/**
	 * �ж�һ��Ԫ���Ƿ���������
	 * @param e	�ж�Ԫ��
	 * @return true ���ж�Ԫ������������
	 * @return false ���ж�Ԫ��û����������
	 * */
	public boolean contain(E e) {
		Node<E> current = head;
		for (int i = 0; i < size; i++) {
			// �����������������Ԫ�أ��ͷ���true
			if (current.element.equals(e))
				return true;
			
			current = current.next;
		}
		
		return false;
	}

	/**
	 * ����ָ���±��Ԫ��
	 * @param index ָ�����±�
	 * @return E 
	 * */
	public E get(int index) {
		// ����±겻�ϸ�ͷ���null
		if (index < 0 || index >= size)
			return null;
		else if (index == 0)	// ����±�Ϊ0���ͷ��ص�һ��Ԫ��
			return getFirst();
		else if (index == size - 1)	// ����±�Ϊsize-1���ͷ������һ��Ԫ��
			return getLast();
		else {	// ���������е�Ԫ��
			Node<E> current = head;
			for (int i = 0; i < index; i++)
				current = current.next;
			
			return current.element;
		}
	}

	/**
	 * ����ָ��Ԫ�ص��±�
	 * @param e ָ����Ԫ��
	 * @return -1 ��������û����ָ����Ԫ��
	 * @return >0 ��ָ��Ԫ�ص��±�
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
	 * �������һ����ָ��Ԫ��ƥ����±�
	 * @param e	ָ����Ԫ��
	 * @return -1 ��������û�����Ԫ��
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
	 * ��ָ����Ԫ�ؽ�ָ���±��Ԫ�ش���
	 * @param index ָ���±�
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
	 * ����±��Ƿ�Ϸ�
	 * */
	private void isIndexRight(int index) {
		if (index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException("�±겻�Ϸ�");
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

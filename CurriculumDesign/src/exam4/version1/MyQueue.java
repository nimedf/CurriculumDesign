package exam4.version1;

public class MyQueue<E> {
	private MyLinkedList<E> list = new MyLinkedList<E>();
	
	/**
	 * ����������һ��Ԫ��
	 * @param e
	 * */
	public void enqueue(E e) {
		list.addLast(e);
	}
	
	/**
	 * �Ӷ�����ɾ��һ��Ԫ��
	 * @return e
	 * */
	public E dequeue() {
		return list.removeFirst();
	}
	
	/**
	 * ���ض����е�Ԫ�ظ���
	 * @return list.size()
	 * */
	public int getSize() {
		return list.size();
	}
}

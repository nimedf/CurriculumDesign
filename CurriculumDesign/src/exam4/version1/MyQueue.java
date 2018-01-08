package exam4.version1;

public class MyQueue<E> {
	private MyLinkedList<E> list = new MyLinkedList<E>();
	
	/**
	 * 向队列中添加一个元素
	 * @param e
	 * */
	public void enqueue(E e) {
		list.addLast(e);
	}
	
	/**
	 * 从队列中删除一个元素
	 * @return e
	 * */
	public E dequeue() {
		return list.removeFirst();
	}
	
	/**
	 * 返回队列中的元素个数
	 * @return list.size()
	 * */
	public int getSize() {
		return list.size();
	}
}

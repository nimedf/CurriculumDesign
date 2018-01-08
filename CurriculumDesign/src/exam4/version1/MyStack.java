package exam4.version1;

public class MyStack<E> {
	private MyArrayList<E> list = new MyArrayList<E>();
	
	/**
	 * 返回并删除这个栈的栈顶元素
	 * @return 
	 * */
	public E pop() {
		E temp = list.get(getSize() - 1);
		list.remove(getSize() - 1);
		
		return temp;
	}
	
	/**
	 * 返回这个栈的栈顶元素
	 * @return 
	 * */
	public E peek() {
		return list.get(getSize() - 1);
	}
	
	/**
	 * 向这个栈的顶端添加一个新元素
	 * @param o 
	 * */
	public void push(E o) {
		list.add(o);
	}
	
	/**
	 * 返回链表里面元素的个数
	 * @return list.size()
	 * */
	public int getSize() {
		return list.size();
	}
	
	/**
	 * 判断栈是否为空
	 * @return true 表示栈是空栈
	 * @return false 栈不是空栈
	 * */
	public boolean isEmpty() {
		return list.isEmpty();
	}
}

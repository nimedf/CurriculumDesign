package exam4.version1;

public class MyStack<E> {
	private MyArrayList<E> list = new MyArrayList<E>();
	
	/**
	 * ���ز�ɾ�����ջ��ջ��Ԫ��
	 * @return 
	 * */
	public E pop() {
		E temp = list.get(getSize() - 1);
		list.remove(getSize() - 1);
		
		return temp;
	}
	
	/**
	 * �������ջ��ջ��Ԫ��
	 * @return 
	 * */
	public E peek() {
		return list.get(getSize() - 1);
	}
	
	/**
	 * �����ջ�Ķ������һ����Ԫ��
	 * @param o 
	 * */
	public void push(E o) {
		list.add(o);
	}
	
	/**
	 * ������������Ԫ�صĸ���
	 * @return list.size()
	 * */
	public int getSize() {
		return list.size();
	}
	
	/**
	 * �ж�ջ�Ƿ�Ϊ��
	 * @return true ��ʾջ�ǿ�ջ
	 * @return false ջ���ǿ�ջ
	 * */
	public boolean isEmpty() {
		return list.isEmpty();
	}
}

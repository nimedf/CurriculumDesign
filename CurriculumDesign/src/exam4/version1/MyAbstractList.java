package exam4.version1;

public abstract class MyAbstractList<E> implements MyList<E> {
	// ��size����ʾ�ϱ�ĳ���
	protected int size = 0;
	
	// ����һ��Ĭ�ϵ�����
	protected MyAbstractList() {
	}
	
	// ����һ�����г�ʼ�����������
	protected MyAbstractList(E[] objects) {
		for (int i = 0; i < objects.length; i++) {
			add(objects[i]);
		}
	}
	
	// ���һ��Ԫ��
	public void add(E e) {
		add(size, e);
	}
	
	// �ж�һ�������Ƿ�Ϊ��
	public boolean isEmpty() {
		return size == 0;
	}
	
	// ��������ĳ���
	public int size() {
		return size;
	}
	
	public boolean remove(E e) {
		if (indexOf(e) >= 0) {
			remove(indexOf(e));
			return true;
		}
		else
			return false;
	}
}

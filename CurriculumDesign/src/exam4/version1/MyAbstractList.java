package exam4.version1;

public abstract class MyAbstractList<E> implements MyList<E> {
	// 用size来表示料表的长度
	protected int size = 0;
	
	// 创建一个默认的链表
	protected MyAbstractList() {
	}
	
	// 创建一个带有初始化数组的链表
	protected MyAbstractList(E[] objects) {
		for (int i = 0; i < objects.length; i++) {
			add(objects[i]);
		}
	}
	
	// 添加一个元素
	public void add(E e) {
		add(size, e);
	}
	
	// 判断一个链表是否为空
	public boolean isEmpty() {
		return size == 0;
	}
	
	// 返回链表的长度
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

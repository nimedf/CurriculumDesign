package exam4.version1;

public interface MyList<E> {
	
	// 添加一个元素到末尾
	public void add(E e);
	
	// 添加一个元素到指定位置
	public void add(int index, E e);
	
	// 清楚链表里的元素
	public void clear();
	
	// 删除链表里的指定元素，如果删除成功则返回true
	public boolean remove(E e);
	
	// 删除链里指定下标位置的元素
	public E remove(int index);
	
	// 判断链表里面是否包含某元素
	public boolean contain(E e);
	
	// 返回指定下标的元素
	public E get(int index);
	
	// 返回指定元素的下标
	public int indexOf(E e);
	
	// 判断链表是否为空
	public boolean isEmpty();
	
	// 返回最后一个和指定元素匹配的下标
	public int lastIndexOf(E e);
	
	// 返回链表的长度
	public int size();
	
	// 用指定元素取代指定下标的元素
	public void set(int index, E e);
}

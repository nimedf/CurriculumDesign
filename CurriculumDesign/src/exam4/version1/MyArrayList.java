package exam4.version1;

public class MyArrayList<E> extends MyAbstractList<E> {
	public static final int DEFAULT_LENGTH = 16;
	private E[] data = (E[]) new Object[DEFAULT_LENGTH];
	
	// 创建一个默认的数组链表
	public MyArrayList() {
	}

	public MyArrayList(E[] objects) {
		super(objects);
	}

	/**
	 * 判断size是否大于数组的长度
	 * 若size > data.length，则吧数组长度变为原来的两倍
	 * 反之，则不变
	 * */
	private void ensureCapacity() {
		if (size >= data.length) {
			E[] newData = (E[])(new Object[size * 2 + 1]);
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}
	
	/**
	 * 在指定小标处添加一个元素
	 * @param index 要添加元素的下标
	 * @param e 要添加的元素
	 * */
	public void add(int index, E e) {
		// 确定数组的剩余的空余空间是否还足够
		ensureCapacity();
		
		// 检测下标是否合格 
		if (index >= data.length || index < 0) {
			throw new ArrayIndexOutOfBoundsException("数组 下标不合法");
		}
		
		// 把index下标后面的元素全部都往后移动一位
		for (int i = size - 1; i >= index; i--) {
			data[i + 1] = data[i];
		}
		
		// 在index下标处添加要插入的元素
		data[index] = e;
		size++;
	}

	/**
	 * 清楚数组链表里面的元素
	 * */
	public void clear() {
		data = (E[])(new Object[DEFAULT_LENGTH]);
		size = 0;
	}

	/**
	 * 删除指定下标处的元素
	 * @param index 所要删除元素的下标
	 * @return 被删除的元素
	 * */
	public E remove(int index) {
		// 检测下标是否合格 
		isIndexOk(index);
		
		// 保存所要删除的元素
		E e = data[index];
		
		// 将所删除元素后面的元素全部向前移动一位
		for (int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
		
		// 因为删除之后的数组列表少了一个所以要把size - 1处赋值为空
		data[size - 1] = null;
		size--;
		
		return e;
	}

	/**
	 * @param e
	 * @return true 数组链表里面包含元素e
	 * #return false 数组链表里面不包含元素e
	 * */
	public boolean contain(E e) {
		for (int i = 0; i < size; i++) {
			if (e.equals(data[i]))
				return true;
		}
		
		return false;
	}

	/**
	 * @param index
	 * @return e 返回指定下标处的元素
	 * */
	public E get(int index) {
		isIndexOk(index);
		return data[index];
	}

	/**
	 * @param e
	 * @return -1 数组链表里面没有这个元素
	 * @return 
	 * */
	public int indexOf(E e) {
		for (int i = 0; i < size; i++) {
			if (e.equals(data[i]))
				return i;
		}
		
		return -1;
	}

	/**
	 * 返回与指定元素匹配的最后一个下标
	 * @param  e
	 * @return -1 数组链表里面没有这个元素
	 * @return
	 * */
	public int lastIndexOf(E e) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (e.equals(data[i]))
				index = i;
		}
		
		return index;
	}

	/**
	 * 用指定元素将指定下标处的元素替代
	 * @param index
	 * @param e
	 * */
	public void set(int index, E e) {
		data[index] = e;
	}

	/**
	 * 判断数组的下标是否合格
	 * @param index
	 * */
	private void isIndexOk(int index) {
		if (index >= size || index < 0) {
			throw new ArrayIndexOutOfBoundsException("数组 下标不合法");
		}
	}
	
	/**
	 *@return elements in the list 
	 **/
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		
		for (int i = 0; i < size; i++) {
			result.append(data[i]);
			if (i < size - 1)
				result.append(", ");
		}
		
		return result.toString() + "]";
	}
	
	/**
	 * 删除空格
	 * */
	public void trimToSize() {
		if (size != data.length) {
			E[] newData = (E[])(new Object[size]);
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}
	
	/**
	 * 返回数组的长度
	 * @return
	 */
	public int getSize() {
		return data.length;
	}
}
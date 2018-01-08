package exam4.version1;

public class MyArrayList<E> extends MyAbstractList<E> {
	public static final int DEFAULT_LENGTH = 16;
	private E[] data = (E[]) new Object[DEFAULT_LENGTH];
	
	// ����һ��Ĭ�ϵ���������
	public MyArrayList() {
	}

	public MyArrayList(E[] objects) {
		super(objects);
	}

	/**
	 * �ж�size�Ƿ��������ĳ���
	 * ��size > data.length��������鳤�ȱ�Ϊԭ��������
	 * ��֮���򲻱�
	 * */
	private void ensureCapacity() {
		if (size >= data.length) {
			E[] newData = (E[])(new Object[size * 2 + 1]);
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}
	
	/**
	 * ��ָ��С�괦���һ��Ԫ��
	 * @param index Ҫ���Ԫ�ص��±�
	 * @param e Ҫ��ӵ�Ԫ��
	 * */
	public void add(int index, E e) {
		// ȷ�������ʣ��Ŀ���ռ��Ƿ��㹻
		ensureCapacity();
		
		// ����±��Ƿ�ϸ� 
		if (index >= data.length || index < 0) {
			throw new ArrayIndexOutOfBoundsException("���� �±겻�Ϸ�");
		}
		
		// ��index�±�����Ԫ��ȫ���������ƶ�һλ
		for (int i = size - 1; i >= index; i--) {
			data[i + 1] = data[i];
		}
		
		// ��index�±괦���Ҫ�����Ԫ��
		data[index] = e;
		size++;
	}

	/**
	 * ����������������Ԫ��
	 * */
	public void clear() {
		data = (E[])(new Object[DEFAULT_LENGTH]);
		size = 0;
	}

	/**
	 * ɾ��ָ���±괦��Ԫ��
	 * @param index ��Ҫɾ��Ԫ�ص��±�
	 * @return ��ɾ����Ԫ��
	 * */
	public E remove(int index) {
		// ����±��Ƿ�ϸ� 
		isIndexOk(index);
		
		// ������Ҫɾ����Ԫ��
		E e = data[index];
		
		// ����ɾ��Ԫ�غ����Ԫ��ȫ����ǰ�ƶ�һλ
		for (int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
		
		// ��Ϊɾ��֮��������б�����һ������Ҫ��size - 1����ֵΪ��
		data[size - 1] = null;
		size--;
		
		return e;
	}

	/**
	 * @param e
	 * @return true ���������������Ԫ��e
	 * #return false �����������治����Ԫ��e
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
	 * @return e ����ָ���±괦��Ԫ��
	 * */
	public E get(int index) {
		isIndexOk(index);
		return data[index];
	}

	/**
	 * @param e
	 * @return -1 ������������û�����Ԫ��
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
	 * ������ָ��Ԫ��ƥ������һ���±�
	 * @param  e
	 * @return -1 ������������û�����Ԫ��
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
	 * ��ָ��Ԫ�ؽ�ָ���±괦��Ԫ�����
	 * @param index
	 * @param e
	 * */
	public void set(int index, E e) {
		data[index] = e;
	}

	/**
	 * �ж�������±��Ƿ�ϸ�
	 * @param index
	 * */
	private void isIndexOk(int index) {
		if (index >= size || index < 0) {
			throw new ArrayIndexOutOfBoundsException("���� �±겻�Ϸ�");
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
	 * ɾ���ո�
	 * */
	public void trimToSize() {
		if (size != data.length) {
			E[] newData = (E[])(new Object[size]);
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}
	
	/**
	 * ��������ĳ���
	 * @return
	 */
	public int getSize() {
		return data.length;
	}
}
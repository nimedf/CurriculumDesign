package exam4.version1;

public interface MyList<E> {
	
	// ���һ��Ԫ�ص�ĩβ
	public void add(E e);
	
	// ���һ��Ԫ�ص�ָ��λ��
	public void add(int index, E e);
	
	// ����������Ԫ��
	public void clear();
	
	// ɾ���������ָ��Ԫ�أ����ɾ���ɹ��򷵻�true
	public boolean remove(E e);
	
	// ɾ������ָ���±�λ�õ�Ԫ��
	public E remove(int index);
	
	// �ж����������Ƿ����ĳԪ��
	public boolean contain(E e);
	
	// ����ָ���±��Ԫ��
	public E get(int index);
	
	// ����ָ��Ԫ�ص��±�
	public int indexOf(E e);
	
	// �ж������Ƿ�Ϊ��
	public boolean isEmpty();
	
	// �������һ����ָ��Ԫ��ƥ����±�
	public int lastIndexOf(E e);
	
	// ��������ĳ���
	public int size();
	
	// ��ָ��Ԫ��ȡ��ָ���±��Ԫ��
	public void set(int index, E e);
}

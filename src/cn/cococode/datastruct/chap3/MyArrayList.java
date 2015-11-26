package cn.cococode.datastruct.chap3;

public class MyArrayList<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private int theSize = 0;
	private T[] storage;
	
	public MyArrayList() {
		clear();
	}
	
	public void clear(){
		theSize = 0;
		expand(DEFAULT_CAPACITY);
	}
	
	public int size(){
		return theSize;
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	public void tirmToCurrentSize(){
		expand(size());
	}
	
	public void add(T item){
		if (size() == storage.length) {
			expand(2 * size());
		}
		
		storage[size()] = item;
		theSize++;
	}
	
	public void add(int index, T item){
		if (size() == storage.length) {
			expand(size() * 2);
		}
		
		for (int i = size(); i > index; i--){
			storage[i] = storage[i-1];
		}
		
		storage[index] = item;
		theSize++;
	}
	
	public T get(int index){
		return storage[index];
	}
	
	public T getLast(){
		return storage[size()-1];
	}
	
	public T removeLast(){
		if (theSize <= 0) {
			return null;
		}
		
		T ret = storage[size()-1];
		theSize--;
		return ret;
	}
	
	public T remove(int index){
		if (index >= theSize) {
			return null;
		}
		
		T ret = storage[index];
		
		for ( int i = index; i <= size()-2; i++){
			storage[i] = storage[i+1];
		}
		
		theSize--;
		return ret;
	}
	
	public void expand(int newCapacity){
		if (newCapacity < theSize) {
			return;
		}
		
		T[] old = storage;
		storage = (T[])new Object[newCapacity];
		for (int i = 0; i < size(); i++){
			storage[i] = old[i];
		}
	}

	@Override
	public String toString() {
		int count = 0;
		StringBuilder sbBuilder = new StringBuilder();
		sbBuilder.append("[");
		for (T t : storage) {
			sbBuilder.append(t).append(",");
			count = (count+1) % 10;
			if (count == 0) {
				sbBuilder.append("\n");
			}
		}
		sbBuilder.deleteCharAt(sbBuilder.toString().lastIndexOf(","));
		sbBuilder.append("]");
		return sbBuilder.toString();
	}
}

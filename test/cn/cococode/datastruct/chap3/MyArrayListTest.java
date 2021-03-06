package cn.cococode.datastruct.chap3;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import cn.cococode.datastruct.chap3.MyArrayList;

public class MyArrayListTest {
	
	MyArrayList<Integer> myArrayList;
	Random rand;
	final int randomSeed = 47;
	final int testSize = 100;
	final int testBound = 10000;
	
	@Before
	public void init(){
		myArrayList = new MyArrayList<Integer>();
		rand = new Random(randomSeed);
	}
	
	@Test
	public void testAdd() throws Exception{

		for(int i = 0; i < testSize; ++i){
			myArrayList.add(rand.nextInt(testBound));
		}
		
		//利用反射获取私有字段theSize
		Field theSizeField = myArrayList.getClass().getDeclaredField("theSize");
		//访问私有字段前先设置访问权限
		theSizeField.setAccessible(true);
		int size = (int)theSizeField.get(myArrayList);
		assertEquals(testSize, size);
		
		Field storageField = MyArrayList.class.getDeclaredField("storage");
		storageField.setAccessible(true);
		
		//对于数组要特殊处理
		Object value = storageField.get(myArrayList);
		//判断该对象是否为数组
		assertTrue(value.getClass().isArray());
		//再转为数组
		Object[] storage = (Object[])value;
		
		rand = new Random(randomSeed);
		for (int i = 0; i < testSize; ++i) {		//注意此处不能用foreach，因为当前返回的数组的长度（很）可能并不等于元素数量
			assertEquals(rand.nextInt(testBound), storage[i]);
		}	
	}
	
	@Test
	public void testAdd2() throws Exception{
		for (int i = 0; i < testSize ; i++) {
			myArrayList.add(i, rand.nextInt(testBound));
		}
		
		Field sizeField = MyArrayList.class.getDeclaredField("theSize");
		//要想访问private变量，一定记得要设置访问权限
		sizeField.setAccessible(true);
		int size = (int)sizeField.get(myArrayList);
		assertEquals(testSize, size);
		
		Field storageField = MyArrayList.class.getDeclaredField("storage");
		storageField.setAccessible(true);
		Object value = storageField.get(myArrayList);
		assertTrue(value.getClass().isArray());
		Object[] storage = (Object[])value;
		rand = new Random(randomSeed);
		for (int i = 0; i < testSize ; i++) {
			assertEquals(rand.nextInt(testBound), (int)storage[i]);
		}
		
		for (int i = 0; i < rand.nextInt(100); ++i){
			int index = rand.nextInt(storage.length);
			int item = rand.nextInt(testBound);
			myArrayList.add(index, item);
			assertEquals(item, storage[index]);
		}
	}

	@Test
	public void testGetLast() {
		for (int i = 0; i < testSize; ++i){
			Integer item = rand.nextInt(testBound);
			myArrayList.add(item);
			assertEquals(item, myArrayList.getLast());
		}
	}
	
	@Test
	public void testGet(){
		fullMyArrayList();
		
		for (int i = 0; i < testSize; ++i){
			assertEquals(i, (int)myArrayList.get(i));
		}
	}

	@Test
	public void testRemoveLast(){
		assertEquals(null, myArrayList.removeLast());
		
		fullMyArrayList();
		
		for(int i = testSize-1; i > 0 ; i--){
			assertEquals(i, myArrayList.getLast().intValue());
			assertEquals(i, myArrayList.removeLast().intValue());
			assertEquals(i-1, myArrayList.getLast().intValue());
		}
	}

	@Test
	public void testRemove(){
		assertEquals(null, myArrayList.remove(rand.nextInt(testBound)));	
	
		for (int i = 0; i < 100; i++){
			myArrayList.clear();
			fullMyArrayList();
			final int size = myArrayList.size();
			int index = rand.nextInt(2 * size) + 1;
			Integer result = myArrayList.remove(index);
			if ( index >= size ) {
				assertEquals(null, result);
			}else if (index < size -1){
				assertEquals(index, result.intValue());
				assertEquals(index+1, myArrayList.get(index).intValue());
				assertEquals(index-1, myArrayList.get(index-1).intValue());
			}else{ //index = size -1
				assertEquals(index, result.intValue());
				assertEquals(index-1, myArrayList.get(index-1).intValue());
			} 
		}
	}
	
	
	@Test
	public void testForeach(){
		fullMyArrayList();
		
		int i = 0;
		for (Integer integer : myArrayList) {
			assertEquals(i++, integer.intValue());
		}
		assertEquals(i, myArrayList.size());
	}
	
	private void fullMyArrayList(){
		for (int i = 0; i < testSize; ++i){
			myArrayList.add(i);
		}
	}
}

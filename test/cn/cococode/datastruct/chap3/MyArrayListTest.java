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
		
		//���÷����ȡ˽���ֶ�theSize
		Field theSizeField = myArrayList.getClass().getDeclaredField("theSize");
		//����˽���ֶ�ǰ�����÷���Ȩ��
		theSizeField.setAccessible(true);
		int size = (int)theSizeField.get(myArrayList);
		assertEquals(testSize, size);
		
		Field storageField = MyArrayList.class.getDeclaredField("storage");
		storageField.setAccessible(true);
		
		//��������Ҫ���⴦��
		Object value = storageField.get(myArrayList);
		//�жϸö����Ƿ�Ϊ����
		assertTrue(value.getClass().isArray());
		//��תΪ����
		Object[] storage = (Object[])value;
		
		rand = new Random(randomSeed);
		for (int i = 0; i < testSize; ++i) {		//ע��˴�������foreach����Ϊ��ǰ���ص�����ĳ��ȣ��ܣ����ܲ�������Ԫ������
			assertEquals(rand.nextInt(testBound), storage[i]);
		}	
	}
	
	@Test
	public void testAdd2() throws Exception{
		for (int i = 0; i < testSize ; i++) {
			myArrayList.add(i, rand.nextInt(testBound));
		}
		
		Field sizeField = MyArrayList.class.getDeclaredField("theSize");
		//Ҫ�����private������һ���ǵ�Ҫ���÷���Ȩ��
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

}

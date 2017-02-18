package com.aires.container;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by 10183966 on 2017/2/17.
 */


/*
*Collection

Collection作为List Queue Set等序列式容器的父接口, 提供了一些公共基础方法:

    update相关方法:
    boolean add(E e)
    boolean addAll(Collection<? extends E> c)
    void clear()
    boolean remove(Object o)
    boolean removeAll(Collection<?> c)
    boolean retainAll(Collection<?> c)(取交集)

    select相关方法
    boolean contains(Object o)
    boolean containsAll(Collection<?> c)
    Iterator<E> iterator()
    Object[] toArray()
    <T> T[] toArray(T[] a)
    boolean isEmpty()
    int size()

详细可参考JDK文档
Iterator

iterator()方法返回一个迭代器Iterator.与其他容器主要用于存储数据不同,Iterator主要用于遍历容器.
Iterator隐藏了各类容器的底层实现细节,向应用程序提供了一个遍历容器的统一接口:
* */
public class RemoveClient {
    Collection<Integer> collection = new ArrayList<>();

    @Before
    public void setUp() {
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            collection.add(random.nextInt(i + 1));
        }
    }
/*注意: 当遍历Collection时不要使用Collection
       自带的remove方法删除数据,确实需要删除时,需要使用Iterator提供的remove.*/
    @Test
    public void client() {
        System.out.print("before:");
        for (Iterator<Integer> iterator = collection.iterator(); iterator.hasNext(); ) {
            Integer integer = iterator.next();
            System.out.printf(" %d", integer);
            if (integer == 0) {
                //collection.remove(i);
                iterator.remove();
            }
        }
        System.out.printf("%n after:");
        for (Integer integer : collection) {
            System.out.printf(" %d", integer);
        }
    }
}

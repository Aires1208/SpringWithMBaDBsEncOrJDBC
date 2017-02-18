package com.aires.container;

import org.junit.Test;

import java.util.*;

/**
 * Created by 10183966 on 2017/2/17.
 *
 * 使用LinkedHashMap统计word出现次数
 */
public class MapClient {
    private Random random = new Random();

    @Test
    public void clientLinkedHashMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        System.out.print("insert key:");
        for (int i = 0; i < 20; ++i) {
            String key = String.valueOf(random.nextInt(10));
            System.out.printf(" %s", key);
            if (map.get(key) == null) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        System.out.printf("%n iterator:");

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.printf(" <%s -> %s>", entry.getKey(), entry.getValue());
        }

    }

    @Test
    public void clientWeakHashMap() {
        /*WeakHashMap与HashMap的区别在于:HashMap的key保留了对实际对象的强引用,
         这意味着只要该HashMap不被销毁,则Map的所有key所引用的对象不会被垃圾回收;
         但WeakHashMap的key只保留对实际对象的弱引用,
         这意味着如果该key所引用的对象没有被其他强引用变量引用,
         则该对象可能被垃圾回收,WeakHashMap也会自动删除这些key对应的key-value对.*/

        Map<String, String> map = new WeakHashMap<>();
        String key = "key";
        map.put(key, "value");
        map.put(new String("key1"), "value");
        map.put(new String("key2"), "value");
        System.out.printf("Before : %d%n", map.size());

        System.gc();
        System.runFinalization();
        System.out.printf("After : %d ", map.size());

        /*如果使用WeakHashMap来保留对象的弱引用,
        则不要让该key所引用的对象具有任何强引用,
        否则将失去使用WeakHashMap的意义.*/
    }

    @Test
    public void clientSortedMap() {
        // value作为期望的order
        SortedMap<Bean, Integer> map = new TreeMap<>();
        map.put(new Bean(true, 3.14, "true"), 1);
        // 该对象与上面的bean compare会返回0
        map.put(new Bean(false, 3.14, "false"), 1);
        map.put(new Bean(true, 3.14, "false"), 2);
        map.put(new Bean(false, 3.14, "true"), 0);
        System.out.println(map);

        Bean firstKey = map.firstKey();
        System.out.printf("first: %s -> %d%n", firstKey, map.get(firstKey));
        Bean lastKey = map.lastKey();
        System.out.printf("last: %s -> %d%n", lastKey, map.get(lastKey));

        map.remove(firstKey);
        Map.Entry<Bean, Integer> firstEntry = ((TreeMap<Bean, Integer>) map).firstEntry();
        System.out.printf("A first: %s -> %d%n", firstEntry.getKey(), firstEntry.getValue());
    }

    private Comparator<Bean> comparator = new Comparator<Bean>() {
        @Override
        public int compare(Bean o1, Bean o2) {
            // 返回正数: 说明o1 > o2
            // 返回负数: 说明o1 < o2
            return -o1.compareTo(o2);
        }
    };

    @Test
    public void clientSortedMap2() {
        SortedMap<Bean, Integer> map = new TreeMap<>(comparator);
        map.put(new Bean(true, 3.14, "true"), 1);
        // 该对象与上面的bean compare会返回0
        map.put(new Bean(false, 3.14, "false"), 1);
        map.put(new Bean(true, 3.14, "false"), 2);
        map.put(new Bean(false, 3.14, "true"), 0);
        System.out.println(map);

        Bean firstKey = map.firstKey();
        System.out.printf("first: %s -> %d%n", firstKey, map.get(firstKey));
        Bean lastKey = map.lastKey();
        System.out.printf("last: %s -> %d%n", lastKey, map.get(lastKey));

        map.remove(firstKey);
        Map.Entry<Bean, Integer> firstEntry = ((TreeMap<Bean, Integer>) map).firstEntry();
        System.out.printf("A first: %s -> %d%n", firstEntry.getKey(), firstEntry.getValue());
        // ...
    }

    /*EnumMap

    EnumMap是一个需要与枚举类一起使用的Map,其所有key都必须是单个枚举类的枚举值.EnumMap具有以下特征:

    EnumMap内部以数组形式存储,紧凑/高效,是Map所有实现中性能最好的.
    EnumMap根据key的自然顺序(枚举值在枚举类的定义顺序)来维护key-value顺序.
    EnumMap不允许key为null, 但允许使用null作为value.
*/

    @Test
    public void clientEnumMap() {
        EnumMap<ShopListType, String> map = new EnumMap<>(ShopListType.class);
        map.put(ShopListType.BLACK_LIST, "BLACK_LIST");
        map.put(ShopListType.WHITE_LIST, "WHITE_LIST");
        System.out.println(map);
    }
}

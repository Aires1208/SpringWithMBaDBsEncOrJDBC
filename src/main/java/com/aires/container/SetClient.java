package com.aires.container;

import org.junit.Test;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by 10183966 on 2017/2/17.
 */
public class SetClient {

    @Test
    public void clientLinkedHashSet() {
        Set<Integer> set = new LinkedHashSet<>();
        for (int i = 0; i < 10; ++i) {
            set.add(i);
        }
        for (int i = 19; i >= 10; --i) {
            set.add(i);
        }
        System.out.println(set);
    }

    @Test
    public void clientEnumSet() {
        EnumSet<ShopListType> set1 = EnumSet.allOf(ShopListType.class);
        System.out.println(set1);

        EnumSet<ShopListType> set2 = EnumSet.noneOf(ShopListType.class);
        System.out.println(set2);
        set2.add(ShopListType.BLACK_LIST);

        System.out.println(set2);
    }
}

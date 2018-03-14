package com.mycp.jclft.utils;

import java.util.Collection;

/**
 * Des:
 * Author: leo
 * Date：2018/2/27.
 */

public class CommUtil {
    /**
     * 判断集合是否为空
     *
     * @param coll
     * @return
     */
    public static boolean isEmpty(Collection<? extends Object> coll) {
        return coll == null || coll.isEmpty();
    }
}

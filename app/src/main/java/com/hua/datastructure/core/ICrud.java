package com.hua.datastructure.core;

/**
 * 增删改查操作
 * @author hua
 * @version V1.0
 * @date 2019/2/18 14:57
 */

public interface ICrud<T> {

    T add(T value);

    boolean delete();
}

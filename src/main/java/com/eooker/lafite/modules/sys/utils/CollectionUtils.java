package com.eooker.lafite.modules.sys.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 集合操作类
 * 
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class CollectionUtils {

	/** 只判断是否为null */
	static boolean isNull(Collection<?> collection) {
		return collection == null;
	}

	/**
	 * 判断是否为null或空集合 判断条件: ==null or isEmpty()
	 **/
	public static boolean isEmpty(Collection<?> collection) {
		return isNull(collection) || collection.isEmpty();
	}

	/**
	 * 判断是否为null或空集合 判断条件: ==null or length == 0
	 **/
	public static boolean isEmpty(Object[] collection) {
		return collection == null || collection.length == 0;
	}

	/** 只判断map是否为null */
	static boolean mapIsNull(Map map) {
		return map == null;
	}

	/**
	 * 判断是否为null或空集合 判断条件: ==null or isEmpty()
	 **/
	public static boolean isEmpty(Map map) {
		return mapIsNull(map) || map.isEmpty();
	}

	/**
	 * 空的List 值 == null
	 **/
	public static final List EmptyList = null;

	/**
	 * 将Collection对象转为List对象
	 * 
	 * @param collection
	 *            collection==null return null; collection is List return
	 *            collection collection isEmpty return ArrayList
	 **/
	public static List toList(Collection collection) {
		if (collection == null) {
			return null;
		} else if (collection instanceof List) {
			return (List) collection;
		} else if (collection.isEmpty()) {
			return new ArrayList();
		} else {
			return new ArrayList(collection);
		}
	}

	/**
	 * 获取两个List的交集
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static List intersection(List first, List second) {
		List result = new ArrayList();
		if (isEmpty(first) || isEmpty(second)) {
			return result;
		} else {
			for (int i = 0; i < first.size(); i++) {
				Object firstObj = first.get(i);
				if (second.contains(firstObj)) {
					result.add(firstObj);
				}
			}
		}
		return result;
	}

	/**
	 * 两个list的合并
	 * 
	 * @param first
	 *            * @param two
	 * @return
	 */
	public static List merge(List first, List second) {
		if (isEmpty(first)) {
			return second;
		}
		if (isEmpty(second)) {
			return first;
		}
		List result = new ArrayList();
		// 开始合并操作
		Set set = new HashSet();
		for (int i = 0; i < first.size(); i++) {
			Object obj = first.get(i);
			if (set.add(obj)) {
				result.add(obj);
			}
		}
		for (int i = 0; i < second.size(); i++) {
			Object obj = second.get(i);
			if (set.add(obj)) {
				result.add(obj);
			}
		}
		return result;
	}

	/**
	 * 功能：集合差集 第一个减去第二个 说明：从第一个集合中提取第二个集合中没有的元素
	 * 
	 * @param first
	 *            第一个集合
	 * @param second
	 *            第二个集合
	 * @return
	 */
	public static List subtract(List first, List second) {
		List resultList = new ArrayList();
		if (isEmpty(first)) {
			return resultList;
		}
		if (isEmpty(second)) {
			return first;
		}

		first.removeAll(second);
		return first;
	}

	/**
	 * 将数组转换成ArrayList
	 * 
	 * @param array
	 * @return
	 */
	public static List toArrayList(Object[] array) {
		return Arrays.asList(array);
	}
}

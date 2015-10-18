package com.yuchengtech.mobile.console.dao;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.util.Assert;

import com.yuchengtech.core.utils.reflection.ReflectionUtils;

/**
 * Hibernate工具类
 *
 */
public class HibernateUtils {

	/**
	 * 根据页面上的checkbox选择 整合Entity的Set
	 * @param srcObjects
	 * @param checkedIds
	 * @param clazz
	 */
	public static <T> void mergeByCheckedIds(final Collection<T> srcObjects,
			final Collection<Long> checkedIds, final Class<T> clazz) {

		Assert.notNull(srcObjects, "scrObjects不能为空");
		Assert.notNull(clazz, "clazz不能为空");

		if (checkedIds == null) {
			srcObjects.clear();
			return;
		}

		Iterator<T> srcIterator = srcObjects.iterator();
		try {

			while (srcIterator.hasNext()) {
				T element = srcIterator.next();
				Long id =(Long) ReflectionUtils.invokeGetterMethod(element, "id");

				if (!checkedIds.contains(id)) {
					srcIterator.remove();
				} else {
					checkedIds.remove(id);
				}
			}

			for (Long id : checkedIds) {
				T element = clazz.newInstance();
				ReflectionUtils.invokeSetterMethod(element, "id", id);
				srcObjects.add(element);
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
}

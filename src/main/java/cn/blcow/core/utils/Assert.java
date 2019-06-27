package cn.blcow.core.utils;

import org.apache.commons.lang3.StringUtils;

import cn.blcow.core.exception.MyRuntimeException;

public abstract class Assert {

	public static void requireStringEmpty(String expression, String msg) {
		if (!StringUtils.isEmpty(expression)) {
			throw new MyRuntimeException(msg);
		}
	}

	public static void requireStringNotEmpty(String expression, String msg) {
		if (StringUtils.isEmpty(expression)) {
			throw new MyRuntimeException(msg);
		}
	}

	public static void requireTrue(boolean expression, String msg) {
		if (!expression) {
			throw new MyRuntimeException(msg);
		}
	}

	public static void requireFalse(boolean expression, String msg) {
		if (expression) {
			throw new MyRuntimeException(msg);
		}
	}

	public static void requireNull(Object expression, String msg) {
		if (expression != null) {
			throw new MyRuntimeException(msg);
		}
	}

	public static void requireNotNull(Object expression, String msg) {
		if (expression == null) {
			throw new MyRuntimeException(msg);
		}
	}

}

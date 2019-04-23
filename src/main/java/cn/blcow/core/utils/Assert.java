package cn.blcow.core.utils;

import cn.blcow.core.exception.MyRuntimeException;

public abstract class Assert {

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

package cn.blcow.core.mvc;

import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections4.map.AbstractMapDecorator;

public class ResultMsg extends AbstractMapDecorator<String, Object> {

	public ResultMsg(boolean success, String msg) {
		super(new HashMap<>());
		put("success", success);
		put("msg", msg);
	}

	public ResultMsg(boolean success, String msg, Object data) {
		this(success, msg);
		put("data", data);
	}

	public ResultMsg(boolean success, Collection<?> data, int total) {
		this(success, null, data);
		put("total", total);
	}

	public static ResultMsg SuccessResultMsg(Object data) {
		return new ResultMsg(true, "操作成功", data);
	}

	public static ResultMsg SuccessResultMsg() {
		return new ResultMsg(true, "操作成功", null);
	}
}

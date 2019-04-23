package cn.blcow.core.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class FormatTimeUtils {

	public static String format(LocalDateTime createTime, LocalDateTime now) {
		String str = "";
		long offsetYear = createTime.until(now, ChronoUnit.YEARS);
		if (offsetYear > 0) {
			str = offsetYear + "年前";
		} else {
			long offsetMonth = createTime.until(now, ChronoUnit.MONTHS);
			if (offsetMonth > 0) {
				str = offsetMonth + "个月前";
			} else {
				long offsetDay = createTime.until(now, ChronoUnit.DAYS);
				if (offsetDay > 0) {
					str = offsetDay + "天前";
				} else {
					long offsetHours = createTime.until(now, ChronoUnit.HOURS);
					if (offsetHours >= 1) {
						str = offsetHours + "个小时前";
					} else {
						long offsetMins = createTime.until(now, ChronoUnit.MINUTES);
						if (offsetMins >= 30) {
							str = "半小时前";
						} else if (offsetMins >= 1) {
							str = offsetMins + "分钟前";
						} else {
							str = "刚刚";
						}
					}
				}
			}
		}
		return str;
	}

}

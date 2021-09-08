package com.grc.risk.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	private DateUtil() {
	}

	public static Timestamp getCurrentUTCTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return Timestamp.valueOf(sdf.format(new Date()));
	}

}

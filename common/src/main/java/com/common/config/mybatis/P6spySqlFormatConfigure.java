package com.common.config.mybatis;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * SQL格式化输出
 *
 * @author yss
 */

public class P6spySqlFormatConfigure implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String s4) {
        return !"".equals(sql.trim()) ? "sql语句: " + sql.replaceAll("[\\t\\n\\r]", "") : "";
    }
}

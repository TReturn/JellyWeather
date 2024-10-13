package com.example.lib_main.utils

/**
 * @CreateDate: 2024/10/13 18:19
 * @Author: 青柠
 * @Description:
 */
object CacheTimeUtils {

    /**
     * 判断两个时间戳之间的时间差是否小于等于 30 分钟。
     *
     * @param timestamp1 第一个时间戳（毫秒）。
     * @param timestamp2 第二个时间戳（毫秒）。
     * @return 如果时间差小于等于 30 分钟，则返回 true；否则返回 false。
     */
    fun isWithin30Minutes(timestamp1: Long, timestamp2: Long): Boolean {
        val diff = kotlin.math.abs(timestamp1 - timestamp2)
        return diff <= 30 * 60 * 1000 // 30 分钟转换为毫秒
    }

    /**
     * 判断两个时间戳之间的时间差是否小于等于 2 小时。
     *
     * @param timestamp1 第一个时间戳（毫秒）。
     * @param timestamp2 第二个时间戳（毫秒）。
     * @return 如果时间差小于等于 2 小时，则返回 true；否则返回 false。
     */
    fun isWithin2Hours(timestamp1: Long, timestamp2: Long): Boolean {
        val diff = kotlin.math.abs(timestamp1 - timestamp2)
        return diff <= 2 * 60 * 60 * 1000 // 2 小时转换为毫秒
    }
}
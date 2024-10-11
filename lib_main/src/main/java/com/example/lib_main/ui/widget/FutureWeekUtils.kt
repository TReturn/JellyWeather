package com.example.lib_main.ui.widget

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

/**
 * @CreateDate: 2024/10/11 18:38
 * @Author: 青柠
 * @Description: 获取未来一周日期、星期
 */
class FutureWeekUtils {

    fun getFutureWeekDates(): List<String> {
        val today = LocalDate.now()
        val futureWeekDates = mutableListOf<String>()
        // 设置日期格式
        val formatter = DateTimeFormatter.ofPattern("MM-dd")

        for (i in 0..6) {
            val futureDate = today.plusDays(i.toLong())
            futureWeekDates.add(futureDate.format(formatter))
        }

        return futureWeekDates
    }

    fun getFutureWeekdays(): List<String> {
        val today = LocalDate.now()
        val futureWeekdays = mutableListOf<String>()

        for (i in 0..6) {
            val futureDate = today.plusDays(i.toLong())
            // 获取星期几
            val weekday = futureDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            futureWeekdays.add(weekday)
        }

        return futureWeekdays
    }
}
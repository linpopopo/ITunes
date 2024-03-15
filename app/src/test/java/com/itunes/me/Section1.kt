package com.itunes.me

import org.junit.Test

class Section1 {

    @Test
    fun joinAllMeeting() {
        // 相交取反
        println(!timeIntersect(listOf(IntRange(0, 30), IntRange(5, 10), IntRange(15, 20))))
        println(!timeIntersect(listOf(IntRange(7, 10), IntRange(2, 4))))
        println(!timeIntersect(listOf(IntRange(1, 5), IntRange(8, 9), IntRange(8, 10))))
    }

    private fun timeIntersect(times: List<IntRange>) : Boolean {
        if (times.isEmpty() || times.onlyOne()) return true
        for (first in times.indices) {
            for (second in first + 1 until times.size) {
                // 只要有一个相交就返回
                if (twoTimeIntersect(listOf(times[first], times[second]))) {
                    return true
                }
            }
        }
        return false
    }

    private fun twoTimeIntersect(times: List<IntRange>): Boolean {
        return if (times.size != 2) {
            false
        } else {
            val time0 = times[0]
            val time1 = times[1]
            // 不相交时
            !(time0.last <= time1.first || time1.last <= time0.first)
        }
    }

    private fun <T> List<T>.onlyOne() = size == 1

}
package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.HydrationEntry
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HydrationRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("hydropulse_prefs", Context.MODE_PRIVATE)

    private fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun loadTodayData(): Pair<Int, List<HydrationEntry>> {
        val savedDate = prefs.getString("last_date", "")
        val today = getTodayDateString()

        if (savedDate != today) {
            // New day: reset today's intake
            saveData(0, emptyList())
            prefs.edit().putString("last_date", today).apply()
            return Pair(0, emptyList())
        }

        val intake = prefs.getInt("current_intake", 0)
        val historyJson = prefs.getString("history_json", "[]") ?: "[]"
        val list = mutableListOf<HydrationEntry>()

        try {
            val jsonArray = JSONArray(historyJson)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    HydrationEntry(
                        id = obj.optLong("id", System.currentTimeMillis()),
                        amountMl = obj.optInt("amount", 250),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                    )
                )
            }
        } catch (_: Exception) {
            // fallback if parse fails
        }

        return Pair(intake, list)
    }

    fun saveData(intakeMl: Int, history: List<HydrationEntry>) {
        val jsonArray = JSONArray()
        for (item in history) {
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("amount", item.amountMl)
            obj.put("timestamp", item.timestamp)
            jsonArray.put(obj)
        }

        prefs.edit()
            .putInt("current_intake", intakeMl)
            .putString("history_json", jsonArray.toString())
            .putString("last_date", getTodayDateString())
            .apply()
    }

    fun getStreak(): Int {
        return prefs.getInt("streak_days", 1)
    }

    fun incrementStreakIfNeeded() {
        val streak = prefs.getInt("streak_days", 1)
        val lastGoalDate = prefs.getString("last_goal_date", "")
        val today = getTodayDateString()
        if (lastGoalDate != today) {
            prefs.edit()
                .putString("last_goal_date", today)
                .putInt("streak_days", streak + 1)
                .apply()
        }
    }
}

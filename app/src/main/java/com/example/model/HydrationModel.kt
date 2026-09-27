package com.example.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HydrationEntry(
    val id: Long = System.currentTimeMillis(),
    val amountMl: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    val formattedTime: String
        get() {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
}

data class HydrationUiState(
    val currentIntakeMl: Int = 0,
    val goalMl: Int = 2000,
    val history: List<HydrationEntry> = emptyList(),
    val streakDays: Int = 1,
    val isResetDialogOpen: Boolean = false,
    val isCustomAmountDialogOpen: Boolean = false,
    val celebrationTrigger: Int = 0
) {
    val progress: Float
        get() = if (goalMl > 0) (currentIntakeMl.toFloat() / goalMl.toFloat()).coerceIn(0f, 1f) else 0f

    val progressPercent: Int
        get() = (progress * 100).toInt()

    val remainingMl: Int
        get() = (goalMl - currentIntakeMl).coerceAtLeast(0)

    val isGoalReached: Boolean
        get() = currentIntakeMl >= goalMl

    val glassesCount: Int
        get() = currentIntakeMl / 250
}

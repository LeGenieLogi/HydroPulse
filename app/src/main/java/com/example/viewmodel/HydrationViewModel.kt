package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.HydrationRepository
import com.example.model.HydrationEntry
import com.example.model.HydrationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HydrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HydrationRepository(application.applicationContext)
    private val _uiState = MutableStateFlow(HydrationUiState())
    val uiState: StateFlow<HydrationUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        val (intake, history) = repository.loadTodayData()
        val streak = repository.getStreak()
        _uiState.update {
            it.copy(
                currentIntakeMl = intake,
                goalMl = 2000,
                history = history,
                streakDays = streak
            )
        }
    }

    /**
     * Ajoute une quantité d'eau (par défaut 250ml)
     */
    fun addWater(amountMl: Int = 250) {
        if (amountMl <= 0) return

        val entry = HydrationEntry(amountMl = amountMl)
        _uiState.update { current ->
            val newIntake = current.currentIntakeMl + amountMl
            val newHistory = listOf(entry) + current.history
            val wasGoalReached = current.isGoalReached
            val isNowGoalReached = newIntake >= current.goalMl
            val newCelebration = if (!wasGoalReached && isNowGoalReached) current.celebrationTrigger + 1 else current.celebrationTrigger

            viewModelScope.launch {
                repository.saveData(newIntake, newHistory)
                if (!wasGoalReached && isNowGoalReached) {
                    repository.incrementStreakIfNeeded()
                }
            }

            current.copy(
                currentIntakeMl = newIntake,
                history = newHistory,
                celebrationTrigger = newCelebration,
                streakDays = if (!wasGoalReached && isNowGoalReached) current.streakDays + 1 else current.streakDays
            )
        }
    }

    /**
     * Réinitialise le suivi à zéro
     */
    fun reset() {
        _uiState.update { current ->
            viewModelScope.launch {
                repository.saveData(0, emptyList())
            }
            current.copy(
                currentIntakeMl = 0,
                history = emptyList(),
                isResetDialogOpen = false
            )
        }
    }

    fun openResetDialog() {
        _uiState.update { it.copy(isResetDialogOpen = true) }
    }

    fun closeResetDialog() {
        _uiState.update { it.copy(isResetDialogOpen = false) }
    }

    fun openCustomDialog() {
        _uiState.update { it.copy(isCustomAmountDialogOpen = true) }
    }

    fun closeCustomDialog() {
        _uiState.update { it.copy(isCustomAmountDialogOpen = false) }
    }

    fun removeEntry(entry: HydrationEntry) {
        _uiState.update { current ->
            val newHistory = current.history.filterNot { it.id == entry.id }
            val newIntake = (current.currentIntakeMl - entry.amountMl).coerceAtLeast(0)
            viewModelScope.launch {
                repository.saveData(newIntake, newHistory)
            }
            current.copy(
                currentIntakeMl = newIntake,
                history = newHistory
            )
        }
    }

    fun undoLast() {
        val last = _uiState.value.history.firstOrNull() ?: return
        removeEntry(last)
    }
}

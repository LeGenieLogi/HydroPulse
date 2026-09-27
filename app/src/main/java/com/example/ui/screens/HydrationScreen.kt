package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.CustomAmountDialog
import com.example.ui.components.GlassesVisualizer
import com.example.ui.components.HydrationActionButtons
import com.example.ui.components.HydrationCelebrationBanner
import com.example.ui.components.HydrationHistoryList
import com.example.ui.components.HydrationProgressCircle
import com.example.ui.components.HydrationTopBar
import com.example.ui.components.ResetConfirmationDialog
import com.example.ui.theme.DarkNavyBackground
import com.example.viewmodel.HydrationViewModel
import kotlinx.coroutines.launch

@Composable
fun HydrationScreen(
    viewModel: HydrationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val haptic = LocalHapticFeedback.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Trigger feedback when goal is reached
    LaunchedEffect(uiState.celebrationTrigger) {
        if (uiState.celebrationTrigger > 0) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            scope.launch {
                snackbarHostState.showSnackbar("Félicitations ! Vous avez atteint votre objectif de 2L 🎉")
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkNavyBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier
            .fillMaxSize()
            .testTag("hydration_screen")
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DarkNavyBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Bar with date and streak
                HydrationTopBar(streakDays = uiState.streakDays)

                // Main Content constrained for tablets & wide screens
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 600.dp)
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Celebration Banner (shows when goal is completed)
                    HydrationCelebrationBanner(isGoalReached = uiState.isGoalReached)

                    // Hero Circular Progress Meter (2L Goal)
                    HydrationProgressCircle(
                        currentIntakeMl = uiState.currentIntakeMl,
                        goalMl = uiState.goalMl,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    // 8-Glasses visual representation (250ml each)
                    GlassesVisualizer(
                        currentIntakeMl = uiState.currentIntakeMl,
                        onAddGlass = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.addWater(250)
                        }
                    )

                    // Action Buttons (+250ml hero button, +100ml, +500ml, +Autre, Reset)
                    HydrationActionButtons(
                        onAdd250ml = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.addWater(250)
                        },
                        onAdd100ml = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.addWater(100)
                        },
                        onAdd500ml = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.addWater(500)
                        },
                        onCustomAdd = {
                            viewModel.openCustomDialog()
                        },
                        onReset = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.openResetDialog()
                        },
                        onUndo = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.undoLast()
                        },
                        canUndo = uiState.history.isNotEmpty()
                    )

                    // Today's Activity Log
                    HydrationHistoryList(
                        history = uiState.history,
                        onRemoveEntry = { entry ->
                            viewModel.removeEntry(entry)
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

    // Dialog: Confirmation de Reset
    if (uiState.isResetDialogOpen) {
        ResetConfirmationDialog(
            onConfirm = {
                viewModel.reset()
                scope.launch {
                    snackbarHostState.showSnackbar("Le suivi de la journée a été réinitialisé à 0 ml.")
                }
            },
            onDismiss = {
                viewModel.closeResetDialog()
            }
        )
    }

    // Dialog: Ajout personnalisé
    if (uiState.isCustomAmountDialogOpen) {
        CustomAmountDialog(
            onAddAmount = { amount ->
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                viewModel.addWater(amount)
                viewModel.closeCustomDialog()
            },
            onDismiss = {
                viewModel.closeCustomDialog()
            }
        )
    }
}

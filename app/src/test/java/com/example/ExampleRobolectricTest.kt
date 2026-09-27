package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.viewmodel.HydrationViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("HydroPulse", appName)
    }

    @Test
    fun `test hydration view model add 250ml and reset`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = HydrationViewModel(app)

        // Reset to initial clean state
        viewModel.reset()
        assertEquals(0, viewModel.uiState.value.currentIntakeMl)
        assertEquals(2000, viewModel.uiState.value.goalMl)
        assertFalse(viewModel.uiState.value.isGoalReached)

        // Add 250 ml
        viewModel.addWater(250)
        assertEquals(250, viewModel.uiState.value.currentIntakeMl)
        assertEquals(1, viewModel.uiState.value.glassesCount)
        assertEquals(1750, viewModel.uiState.value.remainingMl)

        // Add 7 more glasses of 250 ml to reach 2000 ml
        repeat(7) {
            viewModel.addWater(250)
        }
        assertEquals(2000, viewModel.uiState.value.currentIntakeMl)
        assertTrue(viewModel.uiState.value.isGoalReached)
        assertEquals(8, viewModel.uiState.value.glassesCount)
        assertEquals(0, viewModel.uiState.value.remainingMl)

        // Test Reset
        viewModel.reset()
        assertEquals(0, viewModel.uiState.value.currentIntakeMl)
        assertTrue(viewModel.uiState.value.history.isEmpty())
        assertFalse(viewModel.uiState.value.isGoalReached)
    }
}

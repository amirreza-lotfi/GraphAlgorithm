package com.example.graphalgorithms.feature_algoritms.presentation.screen_basic_algorithms.components_of_basic_algorithms.util

sealed class BasicAlgorithmsEvent{
    object OnNavigateToBFSScreen: BasicAlgorithmsEvent()
    object OnNavigateToDFSScreen: BasicAlgorithmsEvent()
}

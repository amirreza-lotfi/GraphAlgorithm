package com.example.graphalgorithms.feature_node.presentation.screen_run_algorithm.screen_short_path_algorithms.screen_dijkstra.util

data class AdjacentOfNode(
    val label:String,
    val distance:Float,
    val visited:Boolean = false
)
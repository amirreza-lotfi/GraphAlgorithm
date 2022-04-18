import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.graphalgorithms.MainActivity
import com.example.graphalgorithms.feature_node.presentation.screen_run_algorithm.screen_dfs_traversal.DfsViewModel
import com.example.graphalgorithms.feature_node.presentation.screen_run_algorithm.screen_dfs_traversal.util.DfsUiEvent
import com.example.graphalgorithms.feature_node.domain.entitiy.Edge
import com.example.graphalgorithms.feature_node.domain.entitiy.Node
import com.example.graphalgorithms.feature_node.presentation.screen_run_algorithm.screen_bfs_traversal.component.*
import com.example.graphalgorithms.feature_node.presentation.screen_run_algorithm.screen_choose_algorithms_screen.component.CustomBackButton
import com.example.graphalgorithms.feature_node.presentation.screen_run_algorithm.screen_dfs_traversal.component.DataComposable
import com.example.graphalgorithms.feature_node.presentation.ui.theme.lightGray
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DFSTraversalScreen(
    navController: NavController,
    startingNode:String,
    dfsViewModel: DfsViewModel = DfsViewModel(
        hiltViewModel(
            navController.getBackStackEntry(MainActivity.CHOOSE_ALGORITHMS_SCREEN_ROUT)
        )
    )
) {
    dfsViewModel.starterNodeForDfsAlgorithms = startingNode

    val coroutineScope = rememberCoroutineScope()
    val visitedNodes = remember{ mutableStateListOf<Node>()}
    val visitedEdges = remember{ mutableStateListOf<Edge>()}

    var visitedNodeText: StringBuffer
    var isPlayButtonVisible by rememberSaveable {
        mutableStateOf(true)
    }


    BoxOfAlgorithm {

        CustomBackButton {
            navController.popBackStack()
        }

        Box(
            Modifier
                .padding(top = 48.dp)
                .fillMaxSize()) {


            DrawGraphEdges(dfsViewModel.edgeList)
            DrawGraphNodes(dfsViewModel.nodeList)

            DrawVisitedEdgeWithRedColor(visitedEdges)
            DrawVisitedNodeWithRedColor(visitedNodes)



            visitedNodeText = dfsViewModel.getVisitedNodeTextForScreen(visitedNodes)


            DataComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .align(Alignment.BottomCenter),
                isPlayButtonVisible = isPlayButtonVisible,
                visitedNodes = visitedNodeText,
                onPlayButtonClick = {
                    visitedNodeText = StringBuffer("")
                    visitedNodes.clear()
                    visitedEdges.clear()
                    dfsViewModel.onPlayButtonClicked()
                    isPlayButtonVisible = false
                }
            )



        }
    }
    coroutineScope.launch {
        dfsViewModel.dfsUiEvent.collect { event ->
            when (event) {
                is DfsUiEvent.OnAlgorithmEnds -> {
                    isPlayButtonVisible = true
                }
                is DfsUiEvent.DrawNodeOnScreen -> {
                    visitedNodes.add(event.node)
                }
                is DfsUiEvent.DrawEdgeOnScreen -> {
                    visitedEdges.add(event.edge)
                }
            }
        }
    }
}





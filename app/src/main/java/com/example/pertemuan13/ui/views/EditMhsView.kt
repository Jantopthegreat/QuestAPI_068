package com.example.pertemuan13.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan13.ui.PenyediaViewModel
import com.example.pertemuan13.ui.customwidget.TopAppBar
import com.example.pertemuan13.ui.navigation.DestinasiNavigasi
import com.example.pertemuan13.ui.viewmodel.EditMhsViewModel
import kotlinx.coroutines.launch

object DestinasiEdit : DestinasiNavigasi {
    override val route = "nim_edit"
    override val titleRes = "Edit Mahasiswa"
    const val nim = "nim"
    val routeWithArgs = "$route/{$nim}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMhsView(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        topBar = {
            TopAppBar(
                title = DestinasiEdit.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ){ innerPadding ->
        EntryBody(
            insertMhsUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.editMahasiswa()
                    navigateBack()
                }
            },
            modifier = modifier.padding(innerPadding)
        )

    }
}
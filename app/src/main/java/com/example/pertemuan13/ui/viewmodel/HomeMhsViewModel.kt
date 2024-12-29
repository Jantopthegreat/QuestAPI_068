package com.example.pertemuan13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.MahasiswaRepository


sealed class HomeMhsUiState{
    data class Success( val mahasiswa: List<Mahasiswa>) : HomeMhsUiState()
    object Error: HomeMhsUiState()
    object Loading: HomeMhsUiState()
}


class HomeMhsViewModel(private val mhs: MahasiswaRepository) : ViewModel(){
    var mhsUiState: HomeMhsUiState by mutableStateOf(HomeMhsUiState.Loading)
}

package com.example.pertemuan13.ui.viewmodel

import com.example.pertemuan13.model.Mahasiswa


sealed class HomeMhsUiState{
    data class Success( val mahasiswa: List<Mahasiswa>) : HomeMhsUiState()
    object Error: HomeMhsUiState()
    object Loading: HomeMhsUiState()
}




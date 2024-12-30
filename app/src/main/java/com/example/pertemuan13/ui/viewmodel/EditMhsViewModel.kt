package com.example.pertemuan13.ui.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan13.repository.MahasiswaRepository
import com.example.pertemuan13.ui.views.DestinasiEdit

import kotlinx.coroutines.launch

class EditMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mahasiswaRepository: MahasiswaRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertMhsUiState())
        private set

    val nim: String = checkNotNull(savedStateHandle[DestinasiEdit.nim])

    init {
        viewModelScope.launch {
            uiState = mahasiswaRepository.getMahasiswaByNim(nim).toUiStateMhs()
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertMhsUiEvent) {
        uiState = InsertMhsUiState(insertMhsUiEvent = insertUiEvent)
    }

    suspend fun editMahasiswa(){
        viewModelScope.launch {
            try {
                mahasiswaRepository.updateMahasiswa(nim, uiState.insertMhsUiEvent.toMhs())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
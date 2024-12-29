package com.example.pertemuan13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.MahasiswaRepository
import kotlinx.coroutines.launch


class InsertMhsViewModel (private val mhs : MahasiswaRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertMhsUiState())
        private set

    fun updateInsertMhsState(insertMhsUiEvent : InsertMhsUiEvent) {
        uiState = InsertMhsUiState(insertMhsUiEvent = insertMhsUiEvent)

    }


    suspend fun InsertMhs() {
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.insertMhsUiEvent.toMhs())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertMhsUiState (
    val insertMhsUiEvent : InsertMhsUiEvent = InsertMhsUiEvent()
)

data class InsertMhsUiEvent(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val jenisKelamin: String = ""
)

fun InsertMhsUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan,
    jenisKelamin = jenisKelamin
)

fun Mahasiswa.toUiStateMhs(): InsertMhsUiState = InsertMhsUiState(
    insertMhsUiEvent = InsertMhsUiEvent()
)

fun Mahasiswa.toInsertMhsUiEvent(): InsertMhsUiEvent = InsertMhsUiEvent(
    nim = nim,
    nama = nama,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan,
    jenisKelamin = jenisKelamin
)


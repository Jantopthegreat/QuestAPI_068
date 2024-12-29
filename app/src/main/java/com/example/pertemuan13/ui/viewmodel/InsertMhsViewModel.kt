package com.example.pertemuan13.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.MahasiswaRepository


class InsertMhsViewModel (private val mhs : MahasiswaRepository) : ViewModel() {}



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

package com.example.pertemuan13.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class HomeMhsUiState{
    data class Success( val mahasiswa: List<Mahasiswa>) : HomeMhsUiState()
    object Error: HomeMhsUiState()
    object Loading: HomeMhsUiState()
}


class HomeMhsViewModel(private val mhs: MahasiswaRepository) : ViewModel() {
    var mhsUiState: HomeMhsUiState by mutableStateOf(HomeMhsUiState.Loading)
        private set


    init {
        getMahasiswa()
    }

    fun getMahasiswa() {
        viewModelScope.launch {
            mhsUiState = HomeMhsUiState.Loading
            mhsUiState = try {
                HomeMhsUiState.Success(mhs.getMahasiswa())
            } catch (e: IOException) {
                HomeMhsUiState.Error
            } catch (e: HttpException) {
                HomeMhsUiState.Error
            }
        }
    }


    fun deleteMhs(nim: String) {
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)

            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: HttpException) {
                HomeMhsUiState.Error
            }
        }
    }
}


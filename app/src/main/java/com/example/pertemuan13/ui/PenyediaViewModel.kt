package com.example.pertemuan13.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan13.MahasiswaApp
import com.example.pertemuan13.ui.viewmodel.DetailMhsViewModel
import com.example.pertemuan13.ui.viewmodel.EditMhsViewModel
import com.example.pertemuan13.ui.viewmodel.HomeMhsViewModel
import com.example.pertemuan13.ui.viewmodel.InsertMhsViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeMhsViewModel(mhsApp().container.mahasiswaRepository) }
        initializer { InsertMhsViewModel(mhsApp().container.mahasiswaRepository) }
        initializer { DetailMhsViewModel(createSavedStateHandle(), mahasiswaRepository = mhsApp().container.mahasiswaRepository) }
        initializer { EditMhsViewModel(createSavedStateHandle(), mahasiswaRepository = mhsApp().container.mahasiswaRepository) }
    }
}

fun CreationExtras.mhsApp(): MahasiswaApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApp)
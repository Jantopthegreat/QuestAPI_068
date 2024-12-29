package com.example.pertemuan13

import android.app.Application
import com.example.pertemuan13.repository.AppContainer
import com.example.pertemuan13.repository.MahasiswaContainer


class MahasiswaApp : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}
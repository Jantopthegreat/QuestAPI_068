package com.example.pertemuan13.repository

import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.service.MahasiswaService

interface MahasiswaRepository{

    suspend fun getMahasiswa(): List<Mahasiswa>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa)

    suspend fun deleteMahasiswa(nim: String)

    suspend fun getMahasiswaByNim(nim: String): Mahasiswa

}

class NetworkKontakRepository(
    private val mahasiswaApiService: MahasiswaService
) : MahasiswaRepository {

    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        mahasiswaApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
            mahasiswaApiService.updateMahasiswa(nim, mahasiswa)
        }


    override suspend fun deleteMahasiswa(nim: String) {
        try {
            val response = mahasiswaApiService.deleteMahasiswa(nim)
            if (!response.isSuccessful) {
                throw Exception("Gagal menghapus mahasiswa, HTTP Status Code: " + "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }

        } catch (e: Exception) {
            throw e

        }

    }
        override suspend fun getMahasiswa(): List<Mahasiswa> {
            return mahasiswaApiService.getMahasiswa()
        }

        override suspend fun getMahasiswaByNim(nim: String): Mahasiswa {
            return mahasiswaApiService.getMahasiswaByNim(nim)
        }
    }


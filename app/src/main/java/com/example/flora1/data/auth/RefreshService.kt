package com.example.flora1.data.auth

import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result

interface RefreshService {
    suspend fun refreshToken() : Result<LoginResponse, DataError.Network>
}

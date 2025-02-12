package com.example.flora1.data.auth

import com.example.flora1.domain.util.DataError
import com.example.flora1.domain.util.Result
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

interface UploadFloatsService {
    suspend fun uploadFloat(
        data: TrainingData = sampleData,
    ): Result<String, DataError.Network>
}

@Serializable
data class TrainingData(
    @SerialName("params")
    val params: List<Float>,

    @SerialName("num_samples")
    val numSamples: Int,
)

val numParams = 321

val sampleData = TrainingData(
    params = List(numParams) { Random.nextFloat() * 1.2f - 0.6f },
    numSamples = 10,
)

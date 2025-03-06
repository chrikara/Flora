package com.example.flora1.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.flora1.domain.util.Error
import io.metamask.androidsdk.Ethereum
import io.metamask.androidsdk.Result
import com.example.flora1.domain.util.Result as FloraResult

sealed interface EthereumError : Error {
    data object NotConnectedToMetamask : EthereumError
    data object DoctorRejected : EthereumError
    data object InvalidParameters : EthereumError
}

interface EthereumWrapper {
    suspend fun connect()

    fun signIn(
        onGetSignature: (FloraResult<String, EthereumError>) -> Unit,
    )

    fun disconnect()
    val selectedAddress: LiveData<String>
    val isConnectedToMetamask: LiveData<Boolean>
}

class SomeModel(
    private val ethereum: Ethereum,
) : EthereumWrapper {

    override suspend fun connect() {
        ethereum.connect{
            println(it)
        }
    }

    override val selectedAddress: LiveData<String> = ethereum.ethereumState
        .map { it.selectedAddress }

    override val isConnectedToMetamask: LiveData<Boolean> = ethereum.ethereumState
        .map { it.selectedAddress.isNotBlank() }

    override fun signIn(
        onGetSignature: (FloraResult<String, EthereumError>) -> Unit,
    ) {
        val message =
            "{\"domain\":{\"chainId\":\"${ethereum.chainId}\",\"name\":\"Ether Mail\",\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\",\"version\":\"1\"},\"message\":{\"contents\":\"Hello, Busa!\",\"from\":{\"name\":\"Kinno\",\"wallets\":[\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\",\"0xDeaDbeefdEAdbeefdEadbEEFdeadbeEFdEaDbeeF\"]},\"to\":[{\"name\":\"Busa\",\"wallets\":[\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\",\"0xB0BdaBea57B0BDABeA57b0bdABEA57b0BDabEa57\",\"0xB0B0b0b0b0b0B000000000000000000000000000\"]}]},\"primaryType\":\"Mail\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Group\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"members\",\"type\":\"Person[]\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\":\"to\",\"type\":\"Person[]\"},{\"name\":\"contents\",\"type\":\"string\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallets\",\"type\":\"address[]\"}]}}"

        selectedAddress.value
            ?.takeIf(String::isNotBlank)
            ?.let { selectedAddress ->
                ethereum.ethSignTypedDataV4(message, selectedAddress) { result ->
                    when (result) {
                        is Result.Success.Item -> {
                            val signature = result.value
                            onGetSignature(FloraResult.Success(signature))
                        }

                        is Result.Error -> {
                            when (result.error.code) {
                                4001 -> { onGetSignature(FloraResult.Error(EthereumError.DoctorRejected)) }
                                -32602 -> onGetSignature(FloraResult.Error(EthereumError.InvalidParameters))
                                else -> onGetSignature(FloraResult.Error(EthereumError.NotConnectedToMetamask))
                            }
                        }

                        else -> Unit
                    }
                }
            }
    }

    override fun disconnect() {
        ethereum.clearSession()
    }
}

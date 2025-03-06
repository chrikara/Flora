package com.example.flora1.features.main

import androidx.lifecycle.asFlow
import io.metamask.androidsdk.Ethereum
import io.metamask.androidsdk.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

interface EthereumWrapper {
    suspend fun connect()
    fun disconnect()
    val selectedAddress : Flow<String>
}

class SomeModel(
    private val ethereum: Ethereum,
) : EthereumWrapper {

    override suspend fun connect() {
        ethereum.connect { result ->
            when (result) {
                is Result.Error -> {
                    println("Ethereum connection error: ${result.error.message}")
                }

                is Result.Success.Item -> {
                    println("Ethereum success item: ${result.value}")
                }

                is Result.Success.ItemMap -> {
                    println("Ethereum success item map: ${result.value}")

                }

                is Result.Success.Items -> {
                    println("Ethereum success items: ${result.value}")

                    val message =
                        "{\"domain\":{\"chainId\":\"${ethereum.chainId}\",\"name\":\"Ether Mail\",\"verifyingContract\":\"0xCcCCccccCCCCcCCCCCCcCcCccCcCCCcCcccccccC\",\"version\":\"1\"},\"message\":{\"contents\":\"Hello, Busa!\",\"from\":{\"name\":\"Kinno\",\"wallets\":[\"0xCD2a3d9F938E13CD947Ec05AbC7FE734Df8DD826\",\"0xDeaDbeefdEAdbeefdEadbEEFdeadbeEFdEaDbeeF\"]},\"to\":[{\"name\":\"Busa\",\"wallets\":[\"0xbBbBBBBbbBBBbbbBbbBbbbbBBbBbbbbBbBbbBBbB\",\"0xB0BdaBea57B0BDABeA57b0bdABEA57b0BDabEa57\",\"0xB0B0b0b0b0b0B000000000000000000000000000\"]}]},\"primaryType\":\"Mail\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"version\",\"type\":\"string\"},{\"name\":\"chainId\",\"type\":\"uint256\"},{\"name\":\"verifyingContract\",\"type\":\"address\"}],\"Group\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"members\",\"type\":\"Person[]\"}],\"Mail\":[{\"name\":\"from\",\"type\":\"Person\"},{\"name\":\"to\",\"type\":\"Person[]\"},{\"name\":\"contents\",\"type\":\"string\"}],\"Person\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"wallets\",\"type\":\"address[]\"}]}}"
                    val address = ethereum.selectedAddress

                    ethereum.ethSignTypedDataV4(message, address) {
                        when (it) {
                            is Result.Error -> {
                                println("Ethereum sign error: ${it.error}")
                            }

                            is Result.Success.Item -> {
                                println("Ethereum sign result: ${it.value}")
                            }

                            is Result.Success.ItemMap -> {
                                println("Ethereum sign itemMap: ${it.value}")

                            }

                            is Result.Success.Items -> {
                                println("Ethereum sign items: ${it.value}")
                            }
                        }
                    }
                }
            }
        }
    }

    override fun disconnect() {
        ethereum.clearSession()
    }

    override val selectedAddress: Flow<String>
        get() = ethereum.ethereumState.asFlow()
            .map { it.selectedAddress }
}

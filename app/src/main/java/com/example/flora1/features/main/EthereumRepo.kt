package com.example.flora1.features.main

import android.content.Context
import io.metamask.androidsdk.DappMetadata
import io.metamask.androidsdk.Ethereum
import io.metamask.androidsdk.Result
import io.metamask.androidsdk.SDKOptions

interface EthereumRepo {
    suspend fun connect()
}

class SomeModel(context: Context) : EthereumRepo {
    private val dappMetadata = DappMetadata("Droid Dapp", "https://www.droiddapp.io")
    private val infuraAPIKey = "92a393fc2d4541e58dabc785f2e4d4f4"
    private val ethereum = Ethereum(context, dappMetadata, SDKOptions(infuraAPIKey, null))

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
}

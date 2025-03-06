package com.example.flora1.core.network

interface ConsentApi : Api {
    override val baseUrl: String
        get() = "http://susanna.athenarc.gr:8090/"
}

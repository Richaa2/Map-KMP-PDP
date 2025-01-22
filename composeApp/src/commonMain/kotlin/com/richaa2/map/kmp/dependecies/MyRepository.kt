package com.richaa2.map.kmp.dependecies

import DbClient

interface MyRepository {
    fun helloWorld(): String
}

class MyRepositoryImpl(
    private val dbClient: DbClient
) : MyRepository {
    override fun helloWorld(): String {
        return "Hello World!"
    }
}
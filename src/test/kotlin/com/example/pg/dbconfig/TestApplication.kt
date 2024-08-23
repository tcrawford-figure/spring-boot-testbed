package com.example.pg.dbconfig

import org.springframework.boot.fromApplication

fun main(args: Array<String>) {
    fromApplication<Application>()
        .with(TestcontainersConfiguration::class.java)
        .run(*args)
}

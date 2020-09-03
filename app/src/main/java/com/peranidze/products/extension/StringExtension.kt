package com.peranidze.products.extension

import com.peranidze.products.ApiConfig

fun String.toFullUrl() = "${ApiConfig.BASE_URL}${this.substring(1)}"

package com.bantutani.application.helper

var formatEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

fun checkEmailFormat(input: String): Boolean {
    return input.trim().matches(formatEmail)
}
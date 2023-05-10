package io.unico.finance

private val MORE_THAN_ONE_WHITESPACE_REGEX = "\\s+".toRegex()

fun String.trimAll() = this.replace("\n", " ")
    .trim()
    .replace(MORE_THAN_ONE_WHITESPACE_REGEX, " ")

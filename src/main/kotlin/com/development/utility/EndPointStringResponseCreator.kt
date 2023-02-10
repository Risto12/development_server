package com.development.utility

object EndPointStringResponseCreator {
    fun endsPointsToString(vararg paths: String) =
        "Endpoints:\n\n" + paths.joinToString(separator = "\n")
}
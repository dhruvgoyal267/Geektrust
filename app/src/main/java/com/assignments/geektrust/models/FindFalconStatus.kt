package com.assignments.geektrust.models

sealed class FindFalconStatus {
    object FalconFinding : FindFalconStatus()
    data class FalconFound(val planet: String) : FindFalconStatus()
    object FalconNotFound : FindFalconStatus()
    data class Error(val errorType: ErrorTypes) : FindFalconStatus()
}

sealed class ErrorTypes(val errorMsg: String?) {
    object TokenNotFound : ErrorTypes("token not found")
    data class OtherError(val msg: String?) : ErrorTypes(msg)
}

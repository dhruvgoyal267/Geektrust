package com.assignments.geektrust.models

sealed class FindFalconStatus {
    object NotFinding : FindFalconStatus()
    object Finding : FindFalconStatus()
    data class Found(val planet: String) : FindFalconStatus()
    object NotFound : FindFalconStatus()
    data class Error(val errorType: ErrorTypes) : FindFalconStatus()
}

sealed class ErrorTypes(val errorMsg: String?) {
    object TokenNotFound : ErrorTypes("token not found")
    data class OtherError(val msg: String?) : ErrorTypes(msg)
}

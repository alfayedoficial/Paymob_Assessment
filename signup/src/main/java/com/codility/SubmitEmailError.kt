package com.codility

sealed class SubmitEmailError: Throwable(){
    object EmailAlreadyInUse : SubmitEmailError()
    object NetworkError : SubmitEmailError()
}

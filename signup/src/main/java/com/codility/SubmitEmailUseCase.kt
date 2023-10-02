package com.codility

import io.reactivex.Completable

interface SubmitEmailUseCase {
    fun execute(email: String): Completable
}
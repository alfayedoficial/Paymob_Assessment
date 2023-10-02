package com.codility

import androidx.annotation.StringRes
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class SignUpToNewsletterViewModel(
    private val submitEmailUseCase: SubmitEmailUseCase, private val router: SubmitEmailRouter, private val schedulerFacade: SchedulerFacade
) : ViewModel() {

    var buttonEnabled = ObservableField<Boolean>()
    var input = ObservableField("")
    var loading = ObservableField(false)
    var errorMessage = ObservableField<@receiver:StringRes Int>()

    init {
        input.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                validateEmail(input.get())
            }
        })
    }

    private fun validateEmail(email: String?) {
        val isValid = isValidEmail(email)
        buttonEnabled.set(isValid)
        errorMessage.set(null)
    }

    private fun isValidEmail(email: String?): Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun onSubmitClick() {
        val email = input.get()
        if (isValidEmail(email)) {
            loading.set(true)
            submitEmailUseCase.execute(email!!).subscribeOn(schedulerFacade.background).observeOn(schedulerFacade.main).subscribe({
                    loading.set(false)
                    router.showSuccess()
                }, { error ->
                    loading.set(false)
                    handleSubmissionError(error)
                })
        }
    }

    private fun handleSubmissionError(error: Throwable) {
        when (error) {
            is SubmitEmailError.EmailAlreadyInUse -> errorMessage.set(R.string.email_already_in_use)
            is SubmitEmailError.NetworkError -> errorMessage.set(R.string.network_error)
            else -> errorMessage.set(null)
        }
    }
}


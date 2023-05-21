package com.example.challengechapterlima.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterViewModel : ViewModel() {
    private val _register = MutableLiveData<String>()
    val register: LiveData<String> = _register

    fun registerFirebase(email: String, password: String) =
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _register.postValue("Register Success!")
            } else {
                _register.postValue(it.exception.toString())
            }
        }


}
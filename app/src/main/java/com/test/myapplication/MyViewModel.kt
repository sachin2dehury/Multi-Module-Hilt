package com.test.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor() : ViewModel() {

    private val _sharedFlow = MutableSharedFlow<Int>()
    private val _stateFlow = MutableStateFlow<Int>(-1)

    val stateFlow = _stateFlow.asStateFlow()
    val sharedFlow = _sharedFlow.asSharedFlow()

    val myClass =  DummyClass()
    init {
//        myClass
        viewModelScope.launch {
            for (i in 0 until 10) {
                _sharedFlow.emit(i)
                _stateFlow.emit(i)
            }
        }
    }
}

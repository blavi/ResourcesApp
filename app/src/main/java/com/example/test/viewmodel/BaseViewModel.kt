package com.example.test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.common.Connectivity
import com.example.data.common.CoroutineContextProvider
import com.example.domain.mvi.action.Action
import com.example.domain.mvi.change.Change
import com.example.domain.mvi.state.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel<C: Change> : ViewModel(){

    protected lateinit var coroutineContext: CoroutineContextProvider
    private lateinit var connectivity: Connectivity
//
//    val userIntent = Channel<A>(Channel.UNLIMITED)
//    open lateinit var _state: MutableStateFlow<S>
//
//    val state: StateFlow<S>
//        get() = _state
//
    lateinit var change: Flow<C>

//    protected val _viewState = MutableLiveData<ViewState<T>>()
//    val viewState: LiveData<ViewState<T>>
//        get() = _viewState
//
//    protected val _viewEffects = MutableLiveData<E>()
//    val viewEffects: LiveData<E>
//        get() = _viewEffects

    protected fun executeUseCase(action: suspend () -> Unit, noInternetAction: () -> Unit) {
//        _state.value = State.Idle
        if (connectivity.hasNetworkAccess()) {
            viewModelScope.launch { action() }
        } else {
            noInternetAction()
        }
    }

    protected fun executeUseCase(action: () -> Flow<C>) {
//        _viewState.value = Loading()
         viewModelScope.launch  {
             change = action()
        }
    }
}
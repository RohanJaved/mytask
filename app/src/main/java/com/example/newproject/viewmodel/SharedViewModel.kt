package com.example.newproject.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.newproject.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val repo: Repo,val app:Application):ViewModel() {

}
package com.darotpeacedude.mvisample.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.darotpeacedude.mvisample.R
import com.darotpeacedude.mvisample.data.api.ApiHelperImpl
import com.darotpeacedude.mvisample.data.api.ApiServiceBuilder
import com.darotpeacedude.mvisample.data.model.User
import com.darotpeacedude.mvisample.ui.main.adapter.MainAdapter
import com.darotpeacedude.mvisample.ui.main.intent.MainIntent
import com.darotpeacedude.mvisample.ui.main.viewmodel.MainViewModel
import com.darotpeacedude.mvisample.ui.main.viewstate.MainViewState
import com.darotpeacedude.mvisample.utils.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }


    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                    DividerItemDecoration(
                            recyclerView.context,
                            (recyclerView.layoutManager as LinearLayoutManager).orientation
                    )
            )
        }
        recyclerView.adapter = adapter
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(
                        ApiHelperImpl(
                                ApiServiceBuilder.apiService
                        )
                )
        ).get(MainViewModel::class.java)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainViewState.Idle -> {

                    }
                    is MainViewState.Loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }

                    is MainViewState.Users -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainViewState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }
}
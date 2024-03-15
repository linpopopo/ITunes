package com.itunes.me

import android.graphics.Rect
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.itunes.me.adapter.ITunesAdapter
import com.itunes.me.databinding.ActivityMainBinding
import com.itunes.me.model.Result
import com.itunes.me.model.state.NetworkState
import com.itunes.me.util.dp
import com.itunes.me.vm.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy { ITunesAdapter() }

    private var loadingDialog: MaterialDialog? = null

    private var sortOff = true

    private val originData by lazy { arrayListOf<Result>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        init()
    }

    private fun initView() {
        binding.musicRv.adapter = adapter
        adapter.setItemAnimation(BaseQuickAdapter.AnimationType.AlphaIn)
        binding.musicRv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.top = dp(12f)
                outRect.bottom = dp(12f)
            }
        })
        binding.musicRv.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.sortRadio.setOnCheckedChangeListener { _, checkedId ->
            val sortOffState = when (checkedId) {
                R.id.sort_off -> true
                R.id.radio_sort_by_price -> false
                else -> false
            }
            // 不同时才切换
            if (sortOffState != sortOff) {
                toggleSort(
                    sortOffState,
                    // 搜索为空使用原始数据，否则使用rv数据
                    if (binding.searchInput.text.isEmpty()) originData else adapter.items
                )
                sortOff = sortOffState
            }
        }

        binding.searchInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId != EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener false
            }
            // 获取搜索字
            val searchText = binding.searchInput.text?.toString()
            if (searchText.isNullOrEmpty()) {
                toggleSort(sortOff, originData)
            } else {
                // 获取含有搜索字眼的条目
                val data = originData.filter {
                    // 忽视大小写
                    it.artistName.equals(searchText, true) || it.trackName.equals(searchText, true)
                }
                toggleSort(sortOff, data)
            }
            return@setOnEditorActionListener true
        }
    }

    private fun init() {
        Looper.myQueue().addIdleHandler {
            viewModel.fetchData()
            false
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect {
                    when (it) {
                        NetworkState.Loading -> showLoading()
                        is NetworkState.Success -> {
                            dismissLoading()
                            originData.clear()
                            originData.addAll(it.data.results)
                            toggleSort(sortOff, originData)
                        }

                        is NetworkState.Error -> {
                            dismissLoading()
                            // 这里简单的toast
                            ToastUtils.showShort("网络错误，请稍后重试")
                        }
                    }
                }
            }
        }
    }

    private fun toggleSort(sortOff: Boolean, list: List<Result>) {
        if (sortOff) {
            adapter.submitList(list)
        } else {
            val sortData = list.sortedBy { res -> res.trackPrice }
                .sortedBy { result -> result.artistName }
            adapter.submitList(sortData)
        }
    }

    private fun showLoading() {
        if (!isFinishing) {
            if (loadingDialog == null) {
                loadingDialog = MaterialDialog(this@MainActivity)
                    .cancelable(true)
                    .cancelOnTouchOutside(false)
                    .cornerRadius(12f)
                    .customView(R.layout.custom_progress_dialog_view)
                    .lifecycleOwner(this@MainActivity)
            }
            loadingDialog?.show()
        }
    }

    private fun dismissLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}
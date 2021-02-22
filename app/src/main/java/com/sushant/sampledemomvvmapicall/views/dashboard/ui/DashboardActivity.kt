package com.sushant.sampledemomvvmapicall.views.dashboard.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.databinding.ActivityDashboardBinding
import com.sushant.sampledemomvvmapicall.model.ProfilerItemData
import com.sushant.sampledemomvvmapicall.model.ProfilerResponse
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.service.model.Status
import com.sushant.sampledemomvvmapicall.views.adapter.BaseViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.ItemAdapter
import com.sushant.sampledemomvvmapicall.views.adapter.NewsViewHolder
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModel

class DashboardActivity : BaseActivity(), ItemAdapter.IAdapterItemListener<ProfilerItemData> {


    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var binding: ActivityDashboardBinding
    private val adapter by lazy {
        ItemAdapter(ArrayList(),this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.lifecycleOwner = this
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        binding.viewModel = dashboardViewModel
        initAdapter()
        requestUserData()
    }

    private fun requestUserData() {
        dashboardViewModel.getUserApiResponse().observe(this, Observer {
            consumeResponse(it)
        })
        dashboardViewModel.getUsers()
    }

    /*
     * method to handle response
     * */
    private fun consumeResponse(apiResponse: ApiResponse<ProfilerResponse>?) {
        when (apiResponse?.status) {
            Status.LOADING -> dashboardViewModel.onShowLoading()
            Status.CLEAR_LIST_HIDE_ERROR -> {
                showErrorView(false)
            }
            Status.SHOW_EMPTY_LIST -> showErrorView(true)
            Status.SUCCESS -> {
                showErrorView(false)
                dashboardViewModel.onStopLoading()
            }
            Status.ERROR -> {
                dashboardViewModel.onStopLoading()
                showErrorView(true)
                Utils.showToast(this, apiResponse.error?.message)
            }
            else ->{
                showErrorView(false)
                hideProgressBar()
            }
        }
    }

    private fun showErrorView(error:Boolean){
        binding.errorView.visibility = if(error) View.VISIBLE else View.GONE
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.swipeRefreshLayout.setOnRefreshListener{
            dashboardViewModel.onRefresh()
            requestUserData()
        }
    }

    override fun onItemClick(pos: Int, data: ProfilerItemData?) {}
    override fun getHolder(parent: ViewGroup): BaseViewHolder<ProfilerItemData> {
        return NewsViewHolder.getInstance(parent)
    }
}
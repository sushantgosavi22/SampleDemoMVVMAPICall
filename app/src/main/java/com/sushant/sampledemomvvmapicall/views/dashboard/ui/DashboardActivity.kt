package com.sushant.sampledemomvvmapicall.views.dashboard.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.databinding.ActivityDashboardBinding
import com.sushant.sampledemomvvmapicall.model.FeedItem
import com.sushant.sampledemomvvmapicall.model.FeedResponse
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.service.model.Status
import com.sushant.sampledemomvvmapicall.views.adapter.BaseViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.ItemAdapter
import com.sushant.sampledemomvvmapicall.views.adapter.FeedNewsViewHolder
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModel

class DashboardActivity : BaseActivity(), ItemAdapter.IAdapterItemListener<FeedItem> {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var binding: ActivityDashboardBinding
    private val adapter by lazy {
        ItemAdapter(ArrayList(),this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.lifecycleOwner = this
        dashboardViewModel = ViewModelProviders.of(this,
            DashboardViewModel.DashboardViewModelFactory(application,FeedRepository(), SavedStateHandle()))
            .get(DashboardViewModel::class.java)
        binding.viewModel = dashboardViewModel
        initAdapter()
        requestFeed()
    }

    private fun requestFeed() {
        dashboardViewModel.getFeedApiResponse().observe(this, Observer {
            consumeResponse(it)
        })

        /**
         * Below code check if there is data available in persisted state.
         * In the case of activity recreated eg. screen rotation, config changes
         * Then restore the persisted data [remove extra api/database call ]
         */
        if(dashboardViewModel.isPersistedAvailable().value==false){
            dashboardViewModel.getFeeds()
            dashboardViewModel.setPersisted(true)
        }
    }

    /*
     * method to handle response
     * */
    private fun consumeResponse(apiResponse: ApiResponse<FeedResponse>?) {
        when (apiResponse?.status) {
            Status.LOADING -> dashboardViewModel.onShowLoading()
            Status.CLEAR_LIST_HIDE_ERROR -> {
                showErrorView(false)
            }
            Status.SHOW_EMPTY_LIST -> showErrorView(true)
            Status.SUCCESS -> {
                showErrorView(false)
                dashboardViewModel.onStopLoading()
                val feedResponse =apiResponse.response as FeedResponse
                handleSuccessResponse(feedResponse)
            }
            Status.ERROR -> {
                dashboardViewModel.onStopLoading()
                showErrorView(true)
                Utils.showToast(this, apiResponse.error?.message)
            }
            else ->{
                showErrorView(false)
                dashboardViewModel.onStopLoading()
            }
        }
    }

    private fun handleSuccessResponse(feedResponse : FeedResponse?){
        title = feedResponse?.title
        feedResponse?.rows?.let { adapter.setList(it) }
    }

    private fun showErrorView(error:Boolean){
        binding.errorView.visibility = if(error) View.VISIBLE else View.GONE
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener{
            dashboardViewModel.onRefresh()
            requestFeed()
        }
    }

    override fun onItemClick(pos: Int, data: FeedItem?) {}
    override fun getHolder(parent: ViewGroup): BaseViewHolder<FeedItem> {
        return FeedNewsViewHolder.getInstance(parent)
    }



}
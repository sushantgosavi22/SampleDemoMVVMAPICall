package com.sushant.sampledemomvvmapicall.views.dashboard.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Constant
import com.sushant.sampledemomvvmapicall.constant.showToast
import com.sushant.sampledemomvvmapicall.databinding.ActivityDashboardBinding
import com.sushant.sampledemomvvmapicall.model.*
import com.sushant.sampledemomvvmapicall.repositorys.feedrepo.FeedRepository
import com.sushant.sampledemomvvmapicall.views.adapter.BaseViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.ItemAdapter
import com.sushant.sampledemomvvmapicall.views.adapter.FeedViewHolder
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModel
import com.sushant.sampledemomvvmapicall.views.details.ui.DetailsActivity

class DashboardActivity : BaseActivity(), ItemAdapter.IAdapterItemListener<FeedItem> {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: ActivityDashboardBinding
    private val adapter by lazy {
        ItemAdapter(arrayListOf(), this)
    }

    override fun getHolder(parent: ViewGroup): BaseViewHolder<FeedItem> =
        FeedViewHolder.getInstance(parent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        binding.lifecycleOwner = this
        dashboardViewModel = ViewModelProviders.of(
            this,
            DashboardViewModel.DashboardViewModelFactory(
                application,
                FeedRepository(),
                SavedStateHandle()
            )
        )
            .get(DashboardViewModel::class.java)
        binding.viewModel = dashboardViewModel
        initToolbar()
        initAdapter()
        requestFeed()
    }

    private fun initToolbar() {
        title = getString(R.string.title_pet)
    }

    private fun requestFeed() {
        dashboardViewModel.getUserApiResponse().observe(this, Observer {
            consumeResponse(it)
        })

        /**
         * Below code check if there is data available in persisted state.
         * In the case of activity recreated eg. screen rotation, config changes
         * Then restore the persisted data [remove extra api/database call ]
         */
        if (dashboardViewModel.isPersistedAvailable().value == false) {
            dashboardViewModel.getFeeds()
            dashboardViewModel.setPersisted(true)
        }
    }

    /*
     * method to handle response
     * */
    private fun consumeResponse(dataResponse: DataResponse<FeedResponse>?) {
        when (dataResponse?.status) {
            Status.LOADING -> {
                adapter.setList(ArrayList())
                dashboardViewModel.onShowLoading()
            }
            Status.CLEAR_LIST_HIDE_ERROR -> {
                showErrorView(false)
            }
            Status.SHOW_EMPTY_LIST -> showErrorView(true)
            Status.SUCCESS -> {
                showErrorView(false)
                dashboardViewModel.onStopLoading()
                val feedResponse = dataResponse.response as FeedResponse
                handleSuccessResponse(feedResponse)
            }
            Status.ERROR -> {
                dashboardViewModel.onStopLoading()
                showErrorView(true)
                showToast(dataResponse.error?.message)
            }
            Status.CONFIG_ERROR -> {
                dashboardViewModel.onStopLoading()
                var errorMessage = getString(R.string.working_hour_message)
                if(dataResponse.error is ConfigFormatError){
                    errorMessage = getString(R.string.working_hour_format_message,dataResponse.error.currentFormat)
                }
                showConfigErrorAlert(errorMessage)
            }
            else -> {
                showErrorView(false)
                dashboardViewModel.onStopLoading()
            }
        }
    }

    private fun showConfigErrorAlert(message: String) {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle(R.string.error_title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.exit)) { _, i ->
                onBackPressed()
            }.show()
    }

    private fun handleSuccessResponse(feedResponse: FeedResponse?) {
        feedResponse?.pets?.let { adapter.updateList(it) }
    }

    private fun showErrorView(error: Boolean) {
        binding.errorView.visibility = if (error) View.VISIBLE else View.GONE
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            /**
             * This callback is used when you want to refresh list, or fetch next page.
             * As per our requirement we doesn't required so commenting out.
             */
            /*dashboardViewModel.onRefresh()
            requestFeed()*/
        }
    }

    override fun onItemClick(pos: Int, data: FeedItem?) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra(Constant.KEY_ITEM, data)
        })
    }
}
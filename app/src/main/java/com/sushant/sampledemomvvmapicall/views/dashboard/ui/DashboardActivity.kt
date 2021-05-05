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
import com.sushant.sampledemomvvmapicall.model.Circles
import com.sushant.sampledemomvvmapicall.model.UserResponse
import com.sushant.sampledemomvvmapicall.model.Users
import com.sushant.sampledemomvvmapicall.service.model.ApiResponse
import com.sushant.sampledemomvvmapicall.service.model.Status
import com.sushant.sampledemomvvmapicall.views.adapter.BaseViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.CircleViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.ItemAdapter
import com.sushant.sampledemomvvmapicall.views.adapter.UserViewHolder
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModel

class DashboardActivity : BaseActivity(), ItemAdapter.IAdapterItemListener<Users> {


    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var binding: ActivityDashboardBinding
    private lateinit var userResponse: UserResponse
    private val userAdapter by lazy {
        ItemAdapter(ArrayList(), this)
    }

    private val circleAdapter by lazy {
        ItemAdapter(ArrayList(), object : ItemAdapter.IAdapterItemListener<Circles> {
            override fun onItemClick(pos: Int, data: Circles?) {}
            override fun getHolder(parent: ViewGroup): BaseViewHolder<Circles> {
                return CircleViewHolder.getInstance(parent)
            }
        })
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
    private fun consumeResponse(apiResponse: ApiResponse<UserResponse>?) {
        when (apiResponse?.status) {
            Status.LOADING -> dashboardViewModel.onShowLoading()
            Status.CLEAR_LIST_HIDE_ERROR -> {
                showErrorView(false)
            }
            Status.SHOW_EMPTY_LIST -> showErrorView(true)
            Status.SUCCESS -> {
                showErrorView(false)
                userResponse = apiResponse.response as UserResponse
                updateList()
                dashboardViewModel.onStopLoading()
            }
            Status.ERROR -> {
                dashboardViewModel.onStopLoading()
                showErrorView(true)
                Utils.showToast(this, apiResponse.error?.message)
            }
            else -> {
                showErrorView(false)
                hideProgressBar()
            }
        }
    }

    private fun updateList(pos: Int = 0) {
        var mList = dashboardViewModel.getUsersFromResponse(userResponse)
        userAdapter.setList(mList, pos)
        circleAdapter.setList(
            dashboardViewModel.getCirclesFromResponse(
                userResponse,
                mList.get(pos)
            ), pos
        )
    }

    private fun showErrorView(error: Boolean) {
        binding.errorView.visibility = if (error) View.VISIBLE else View.GONE
    }

    private fun initAdapter() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewUser.layoutManager = layoutManager
        binding.recyclerViewUser.adapter = userAdapter
        binding.recyclerViewUser.setHasFixedSize(true)
        binding.swipeRefreshLayout.setOnRefreshListener {
            dashboardViewModel.onRefresh()
            requestUserData()
        }

        val layoutManagerCircle = LinearLayoutManager(this)
        layoutManagerCircle.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewCircle.layoutManager = layoutManagerCircle
        binding.recyclerViewCircle.adapter = circleAdapter
        binding.recyclerViewCircle.setHasFixedSize(true)
    }

    override fun onItemClick(pos: Int, data: Users?) {
        updateList(pos)
    }

    override fun getHolder(parent: ViewGroup): BaseViewHolder<Users> {
        return UserViewHolder.getInstance(parent)
    }
}
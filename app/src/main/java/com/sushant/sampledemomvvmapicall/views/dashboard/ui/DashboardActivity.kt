package com.sushant.sampledemomvvmapicall.views.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
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
import com.sushant.sampledemomvvmapicall.views.details.ui.DetailsActivity

class DashboardActivity : BaseActivity(), ItemAdapter.IAdapterItemListener<ProfilerItemData> {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var adapter: ItemAdapter<ProfilerItemData,NewsViewHolder>? = null
    lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        requestUserData()
    }

    private fun requestUserData() {
        dashboardViewModel.getUserApiResponse().observe(this, Observer {
            consumeResponse(it)
        })
        dashboardViewModel.getUsers(Utils.FIRST_PAGE)
    }

    /*
     * method to handle response
     * */
    private fun consumeResponse(apiResponse: ApiResponse<ProfilerResponse>?) {
        when (apiResponse?.status) {
            Status.LOADING -> showProgressBar()
            Status.SUCCESS -> {
                hideProgressBar()
                val response =apiResponse.response as ProfilerResponse
                setRecyclerView(response.data)
            }
            Status.ERROR -> {
                hideProgressBar()
                showEmptyView(true)
                binding.emptyView.text = apiResponse.error?.message
                Utils.showToast(this, apiResponse.error?.message)
            }
            else ->{
                hideProgressBar()
            }
        }
    }
    private fun setRecyclerView(dataList: List<ProfilerItemData>?) {
        dataList?.let {
            showEmptyView(false)
            adapter = ItemAdapter(this)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            binding.recyclerView.layoutManager = layoutManager
            adapter?.setList(dataList)
            binding.recyclerView.adapter = adapter
        } ?: showEmptyView(true)
    }

    private fun showEmptyView(isEmpty: Boolean) {
        binding.emptyView.visibility = if (isEmpty) {
            VISIBLE
        } else {
            GONE
        }
        binding.recyclerView.visibility = if (isEmpty) {
            GONE
        } else {
            VISIBLE
        }
    }

    override fun onItemClick(pos: Int, data: ProfilerItemData?) {
        openDetailActivity(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.add -> {
                openDetailActivity(null)
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.KEY_REQUEST_ID && resultCode == Activity.RESULT_OK) {
            requestUserData()
        }
    }

    private fun openDetailActivity(item: ProfilerItemData?) {
        var intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Utils.KEY_ITEM, item)
        startActivityForResult(intent, Utils.KEY_REQUEST_ID)
    }

    override fun getHolder(parent: ViewGroup): BaseViewHolder<ProfilerItemData> {
        return NewsViewHolder.getInstance(parent)
    }
}
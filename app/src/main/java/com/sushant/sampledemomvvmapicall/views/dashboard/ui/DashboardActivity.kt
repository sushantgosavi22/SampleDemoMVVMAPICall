package com.sushant.sampledemomvvmapicall.views.dashboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sushant.sampledemomvvmapicall.R
import com.sushant.sampledemomvvmapicall.constant.Utils
import com.sushant.sampledemomvvmapicall.model.Customers
import com.sushant.sampledemomvvmapicall.views.adapter.OrderAdapter
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModel
import com.sushant.sampledemomvvmapicall.views.details.ui.DetailsActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : BaseActivity(), OrderAdapter.IOnOrderClickListener,
    DashboardViewModel.ICustomerListCallBack {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var selectedPosition: Int = 0
    private var customers: Customers? = null
    private var adapter: OrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        callOrderApi()
    }

    private fun callOrderApi() {
        showProgressBar()
        dashboardViewModel.getCustomers(this)
    }

    private fun setRecyclerView(dataList: List<Customers>?) {
        dataList?.let {
            showEmptyView(false)
            adapter = OrderAdapter(this)
            val categoryLinearLayoutManager = LinearLayoutManager(this)
            categoryLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerOrderList.layoutManager = categoryLinearLayoutManager
            adapter?.setList(dataList)
            recyclerOrderList.adapter = adapter
        } ?: showEmptyView(true)
    }

    private fun showEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) {
            VISIBLE
        } else {
            GONE
        }
        recyclerOrderList.visibility = if (isEmpty) {
            GONE
        } else {
            VISIBLE
        }
    }

    override fun onOrderClick(pos: Int, data: Customers?) {
        selectedPosition = pos
        customers = data
        startActivityForResult(
            Intent(this, DetailsActivity::class.java).apply { putExtra(Utils.KEY_CUSTOMERS, data) },
            Utils.KEY_REQUEST_ID
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.KEY_REQUEST_ID && resultCode == Activity.RESULT_OK) {
            adapter?.let {
                customers?.isActive = true
                it.updateItem(selectedPosition, customers)
            }
        }
    }

    override fun onCallBack(success: Boolean, data: List<Customers>?, t: Throwable?) {
        if (success) {
            setRecyclerView(data)
        } else {
            showEmptyView(true)
            emptyView.text = t?.message
            Utils.showToast(this, t?.message)
        }
        hideProgressBar()
    }
}
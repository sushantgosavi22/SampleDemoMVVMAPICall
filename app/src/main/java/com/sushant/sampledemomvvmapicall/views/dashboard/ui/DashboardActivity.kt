package com.sushant.sampledemomvvmapicall.views.dashboard.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
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
import com.sushant.sampledemomvvmapicall.views.adapter.FeedViewHolder
import com.sushant.sampledemomvvmapicall.views.adapter.ItemAdapter
import com.sushant.sampledemomvvmapicall.views.base.BaseActivity
import com.sushant.sampledemomvvmapicall.views.base.EndlessScrollListener
import com.sushant.sampledemomvvmapicall.views.dashboard.viewmodel.DashboardViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.concurrent.TimeUnit


class DashboardActivity : BaseActivity(), ItemAdapter.IAdapterItemListener<FeedItem> {

    private lateinit var dashboardViewModel: DashboardViewModel
    lateinit var binding: ActivityDashboardBinding
    lateinit var endlessScrollListener: EndlessScrollListener
    var searchableList: ArrayList<FeedItem> = arrayListOf()
    val handler = Handler(Looper.getMainLooper())
    var runnable = object : Runnable {
        override fun run() {
            val offset = endlessScrollListener.getFirstVisibleItemCount()
            if(offset >= 0) dashboardViewModel.getFeeds(offset = offset)
            handler.postDelayed(this, 10000)
        }
    }

    private val adapter by lazy {
        ItemAdapter(ArrayList(), this)
    }

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
        if (dashboardViewModel.isPersistedAvailable().value == false) {
            dashboardViewModel.setPersisted(true)
            handler.post(runnable)
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
                val feedResponse = apiResponse.response as FeedResponse
                handleSuccessResponse(feedResponse)
            }
            Status.ERROR -> {
                dashboardViewModel.onStopLoading()
                showErrorView(true)
                Utils.showToast(this, apiResponse.error?.message)
            }
            else -> {
                showErrorView(false)
                dashboardViewModel.onStopLoading()
            }
        }
    }

    private fun handleSuccessResponse(feedResponse: FeedResponse?) {
        val currentList = adapter.list
        feedResponse?.data?.forEach { item ->
            if (currentList.contains(item)) {
                val index = adapter.list.indexOf(item)
                val oldItem = adapter.list[index]
                item.isPriceUp = isPriceUp(oldItem, item)
                adapter.updateItem(index, item)
            } else {
                adapter.addItem(item)
            }
        }
    }

    private fun isPriceUp(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return try {
            val oldUsd = oldItem.priceUsd?.toDouble()
            val updatedUsd = newItem.priceUsd?.toDouble()
            return if (oldUsd != null && updatedUsd != null) {
                oldUsd <= updatedUsd
            } else {
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    private fun showErrorView(error: Boolean) {
        binding.errorView.visibility = if (error) View.VISIBLE else View.GONE
    }

    private fun initAdapter() {
        title = getString(R.string.app_name)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        this.endlessScrollListener =
            object : EndlessScrollListener(binding.recyclerView.layoutManager!!) {
                override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
                    if (totalItemCount > 1 && currentPage > 0) {
                        val offset = currentPage * Utils.FEED_LIMIT
                        dashboardViewModel.getFeeds(offset = offset)
                    }
                }

                override fun onScroll(firstVisibleItem: Int, dy: Int, scrollPosition: Int) {}
            }
        recyclerView.addOnScrollListener(endlessScrollListener)

        binding.swipeRefreshLayout.setOnRefreshListener {
            resetPagination()
        }
    }

    private fun resetPagination() {
        adapter.setList(ArrayList())
        endlessScrollListener.reset()
        dashboardViewModel.getFeeds()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        fromView(searchView, searchItem)
            .debounce(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { text ->
                text.isNotEmpty()
            }
            .distinctUntilChanged()
            .switchMap { search: String ->
                adapter.setList(ArrayList())
                Observable.fromIterable(searchableList)
                    .subscribeOn(Schedulers.io())
                    .filter { it.name?.contains(search, ignoreCase = true) == true }
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribeBy {
                adapter.addItem(it)
            }

        return super.onCreateOptionsMenu(menu)
    }


    override fun onItemClick(pos: Int, data: FeedItem?) {}
    override fun getHolder(parent: ViewGroup): BaseViewHolder<FeedItem> {
        return FeedViewHolder.getInstance(parent)
    }

    private fun fromView(searchView: SearchView, searchItem: MenuItem?): Observable<String> {
        val subject = PublishSubject.create<String>()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean = true
            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return true
            }
        })
        searchItem?.setOnActionExpandListener(
            object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    //clear list
                    adapter.setList(arrayListOf())
                    //add previous data
                    searchableList.sortBy { it.rank?.toInt() }
                    adapter.setList(searchableList)
                    handler.postDelayed(runnable, 10000)
                    return true
                }

                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    //After every 10 second list update while filtering also so need to remove callback
                    handler.removeCallbacks(runnable)
                    searchableList = adapter.getSearchableList()
                    return true
                }
            })
        return subject
    }
}
package com.android.sampledemomvvmapicall.github.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.android.sampledemomvvmapicall.github.AppExecutors
import com.android.sampledemomvvmapicall.github.R
import com.android.sampledemomvvmapicall.github.binding.FragmentDataBindingComponent
import com.android.sampledemomvvmapicall.github.databinding.RepoFragmentBinding
import com.android.sampledemomvvmapicall.github.di.Injectable
import com.android.sampledemomvvmapicall.github.ui.common.RetryCallback
import com.android.sampledemomvvmapicall.github.util.autoCleared
import javax.inject.Inject

/**
 * The UI Controller for displaying a Github Repo's information with its contributors.
 */
class RepoFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val repoViewModel: RepoViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    // mutable for testing
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<RepoFragmentBinding>()

    private val params by navArgs<RepoFragmentArgs>()
    private var adapter by autoCleared<ContributorAdapter>()

    private fun initContributorList(viewModel: RepoViewModel) {
        viewModel.contributors.observe(viewLifecycleOwner, Observer { listResource ->
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            if (listResource?.data != null) {
                adapter.submitList(listResource.data)
            } else {
                adapter.submitList(emptyList())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<RepoFragmentBinding>(
            inflater,
            R.layout.repo_fragment,
            container,
            false
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                repoViewModel.retry()
            }
        }
        binding = dataBinding
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repoViewModel.setId(params.owner, params.name)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.avatarUrl = params.avatarUrl ?: ""
        binding.repo = repoViewModel.repo

        val adapter = ContributorAdapter(dataBindingComponent, appExecutors) { _, _ ->
        }
        this.adapter = adapter
        binding.contributorList.adapter = adapter
        postponeEnterTransition()
        binding.contributorList.doOnPreDraw {
            startPostponedEnterTransition()
        }
        initContributorList(repoViewModel)
    }
}

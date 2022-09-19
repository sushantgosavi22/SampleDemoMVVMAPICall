package com.android.sampledemomvvmapicall.github

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.android.sampledemomvvmapicall.github.util.setStatusBarGradiant
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarGradiant()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}

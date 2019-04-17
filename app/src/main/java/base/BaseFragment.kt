package base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.topnews.R

abstract class BaseFragment<VM : ViewModel> : Fragment() {

	protected open var TAG: String = "BaseFragment"

	protected var actionBar: ActionBar? = null

	protected val viewModel: VM by lazy {
		ViewModelProviders.of(this).get(getClassTypeVM())
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Log.d(TAG, "onCreate")
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		Log.d(TAG, "onCreateView: ")
		 val inflated = inflater.inflate(getLayoutId(), container, false)
		setHasOptionsMenu(true)
		return inflated
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		Log.d(TAG, "onViewCreated: ")
		initView()
	}

	override fun onStart() {
		Log.d(TAG, "onStart: ")
		super.onStart()
	}

	override fun onResume() {
		Log.d(TAG, "onResume: ")
		super.onResume()
	}

	override fun onPause() {
		Log.d(TAG, "onPause: ")
		super.onPause()
	}

	override fun onStop() {
		Log.d(TAG, "onStop: ")
		super.onStop()
	}


	override fun onDestroyView() {
		Log.d(TAG, "onDestroyView: ")
		super.onDestroyView()
	}

	override fun onDestroy() {
		Log.d(TAG, "onDestroy: ")
		super.onDestroy()
	}

	abstract fun initView()

	@LayoutRes
	abstract fun getLayoutId(): Int

	abstract fun getClassTypeVM(): Class<VM>

	protected fun setActionBar(toolbar: Toolbar, up: Boolean = false) {
		(activity as AppCompatActivity).setSupportActionBar(toolbar)
		val supportActionBar = (activity as AppCompatActivity).supportActionBar
		supportActionBar?.setDisplayHomeAsUpEnabled(up)
		actionBar = supportActionBar
	}

	protected fun handleSearchMenu(menuItem: MenuItem) {
		menuItem.setOnMenuItemClickListener {
			Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.action_global_searchFragment)
			true
		}
	}

}
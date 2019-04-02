package base


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.topnews.R
import kotlinx.android.synthetic.main.activity_frame.*

abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected var actionBar: ActionBar? = null

    protected val viewModel: VM by lazy {
        ViewModelProviders.of(this).get(getClassTypeVM())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflated = inflater.inflate(getLayoutId(), container, false)
        setHasOptionsMenu(true)
        return inflated
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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

    protected fun handleSearchMenu(menuItem: MenuItem){
        menuItem.setOnMenuItemClickListener {
            Navigation.findNavController(activity!!,R.id.nav_host_fragment).navigate(R.id.action_global_searchFragment)
            true
        }
    }

}
package base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(getViewLayout())
		//        setSupportActionBar(toolbar_top)
		initView()
	}

	@LayoutRes
	abstract fun getViewLayout(): Int

	abstract fun initView()
}
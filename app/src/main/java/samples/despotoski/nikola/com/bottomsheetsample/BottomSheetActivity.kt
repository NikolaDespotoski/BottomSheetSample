package samples.despotoski.nikola.com.bottomsheetsample

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_1.view.*
import samples.despotoski.nikola.com.bottomsheetsample.databinding.ActivityBottomSheetBinding
import java.util.*

class BottomSheetActivity : AppCompatActivity() {
    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    lateinit var binding : ActivityBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_bottom_sheet)

        setSupportActionBar(binding.toolbar)

        setupBottomSheet1()

        setupBottomSheet2()
    }

    private fun setupBottomSheet2() {
        binding.showBottomSheet2.setOnClickListener {
            val bottomSheetDialogFragment: BottomSheetDialogFragment = CustomBottomSheetDialogFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }

    private fun setupBottomSheet1() {
        binding.showBottomSheet1.setOnClickListener { //Let's peek it, programmatically
            mBottomSheetBehavior?.peekHeight = binding.layoutBottomSheet1.textViewDragMe.height
            binding.layoutBottomSheet1.textViewDragMe.requestLayout()
        }

        val recyclerView = binding.layoutBottomSheet1.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ApplicationAdapter(this, listApplications(this))
        val parentThatHasBottomSheetBehavior = recyclerView.parent.parent as FrameLayout
        mBottomSheetBehavior = BottomSheetBehavior.from(parentThatHasBottomSheetBehavior)
        mBottomSheetBehavior?.let {
            binding.stateText.setText(getStateAsString(it.state))
            it.setBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    binding.stateText.setText(getStateAsString(newState))
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    binding.offsetText.text = getString(R.string.offset, slideOffset)
                }
            })
        }
    }

    override fun onBackPressed() {
        mBottomSheetBehavior?.let {
            if (it.state != BottomSheetBehavior.STATE_HIDDEN) {
                it.setState(BottomSheetBehavior.STATE_COLLAPSED)
            } else {
                super.onBackPressed()
            }
        }

    }

    companion object {
        fun getStateAsString(newState: Int): Int {
            when (newState) {
                BottomSheetBehavior.STATE_COLLAPSED -> return R.string.collapsed
                BottomSheetBehavior.STATE_DRAGGING -> return R.string.dragging
                BottomSheetBehavior.STATE_EXPANDED -> return R.string.expanded
                BottomSheetBehavior.STATE_HIDDEN -> return R.string.hidden
                BottomSheetBehavior.STATE_SETTLING -> return R.string.settling
            }
            return R.string.undefined
        }

        fun listApplications(context: Context): List<ApplicationInfo> {
            val flags = PackageManager.GET_META_DATA
            val installedApps: MutableList<ApplicationInfo> = ArrayList()
            val pm = context.packageManager
            val applications = pm.getInstalledApplications(flags)
            for (appInfo in applications) {
                if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 1) {
                    installedApps.add(appInfo)
                }
            }
            return installedApps
        }
    }
}
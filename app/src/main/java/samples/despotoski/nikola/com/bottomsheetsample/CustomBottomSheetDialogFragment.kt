package samples.despotoski.nikola.com.bottomsheetsample

import android.app.Dialog
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Nikola D. on 2/25/2016.
 */
class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var dialogOffsetText: TextView? = null
    private var dialogStateText: TextView? = null
    private val bottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            setStateText(newState)
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            setOffsetText(slideOffset)
        }
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.dialog_bottom_sheet_2, null)
        dialog.setContentView(contentView)
        val layoutParams = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = layoutParams.behavior
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.addBottomSheetCallback(bottomSheetBehaviorCallback)
        }
        dialogOffsetText = contentView.findViewById<View>(R.id.dialogOffsetText) as TextView
        dialogStateText = contentView.findViewById<View>(R.id.dialogStateText) as TextView
    }

    private fun setOffsetText(slideOffset: Float) {
        ViewCompat.postOnAnimation(dialogOffsetText!!) { dialogOffsetText!!.text = getString(R.string.offset, slideOffset) }
    }

    private fun setStateText(newState: Int) {
        dialogStateText!!.setText(BottomSheetActivity.getStateAsString(newState))
    }
}
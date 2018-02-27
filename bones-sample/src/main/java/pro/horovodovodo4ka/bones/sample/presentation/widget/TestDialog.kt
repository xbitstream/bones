package pro.horovodovodo4ka.bones.sample.presentation.widget

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test_dialog.*
import pro.horovodovodo4ka.bones.Phalanx
import pro.horovodovodo4ka.bones.extensions.dismiss
import pro.horovodovodo4ka.bones.sample.R
import pro.horovodovodo4ka.bones.ui.FragmentSibling
import pro.horovodovodo4ka.bones.ui.delegates.Page
import java.util.*

class WidgetDialogBone(val initialValue: Date?, private val callback: (Date) -> Unit) : Phalanx() {
    override val seed = { TestDialog() }

    fun setDate(date: Date) {
        callback(date)
    }
}

class TestDialog : DialogFragment(),
    FragmentSibling<WidgetDialogBone> by Page() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_test_dialog, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()

        bone.initialValue?.also { calendar.time = it }

        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, date ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, date)

            bone.setDate(calendar.time)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        bone.dismiss()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }
}
package gsc.ZupStar.util

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.material.bottomsheet.BottomSheetDialog
import gsc.ZupStar.databinding.BottomsheetMapLogBinding
import gsc.ZupStar.ui.map.MissionLogRVAdapter

fun Context.showBottomSheet(
    title: List<String>,
    context: Context
) {
    val binding = BottomsheetMapLogBinding.inflate(LayoutInflater.from(this))
    val dialog = BottomSheetDialog(this)

    val adapter = MissionLogRVAdapter(title)
    binding.rvMapLog.adapter = adapter
    binding.rvMapLog.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

    dialog.setContentView(binding.root)
    dialog.show()
}
//enum class BottomSheetAction(@StringRes val textResId: Int) {
//    EDIT(R.string.text_edit),
//    DELETE(R.string.text_delete),
//    REPORT(R.string.text_report)
//}

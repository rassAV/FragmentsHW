package com.example.fragmentshw

import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class MyDialog : DialogFragment(){
    var onPositiveClickListener: OnClickListener? = null
    var onNegativeClickListener: OnClickListener? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage("Choose weather variable view")
            .setPositiveButton("Short") { dialogInterface, which ->
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, ShortWeather())
                    .commitAllowingStateLoss()
                onPositiveClickListener?.onClick(dialog, which)
                dismiss()
            }
            .setNegativeButton("Detail") { dialogInterface, which ->
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, DetailWeather())
                    .commitAllowingStateLoss()
                onNegativeClickListener?.onClick(dialog, which)
                dismiss()
            }
            .create()
            .apply {
                setCancelable(false)
                setCanceledOnTouchOutside(false)
            }
    }
}
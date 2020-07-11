    package com.example.zaitoneh

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.zaitoneh.storetracker.StoreTrackerFragment

    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyDialog(storeTrackerFragment: StoreTrackerFragment) : DialogFragment() {
    lateinit var storeFragment:StoreTrackerFragment

    init {
        storeFragment=storeTrackerFragment
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (arguments != null)
        {
            if (arguments?.getBoolean("notAlertDialog")!!)
            {
                return super.onCreateDialog(savedInstanceState)
            }
        }
        val builder = AlertDialog.Builder(activity)

        builder.setPositiveButton("Cool", object: DialogInterface.OnClickListener {
            override fun onClick(dialog:DialogInterface, which:Int) {
                dismiss()
            }
        })
        builder.setNegativeButton("Cancel", object: DialogInterface.OnClickListener {
            override fun onClick(dialog:DialogInterface, which:Int) {
                dismiss()
            }
        })
        return builder.create()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_dialog, container, false)
    }
    override fun onViewCreated(view:View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = view.findViewById<EditText>(R.id.inMobile)
        if (arguments != null && !TextUtils.isEmpty(arguments?.getString("mobile")))
            editText.setText(arguments?.getString("mobile"))
        val btnDone = view.findViewById<Button>(R.id.btnDone)
        btnDone.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view:View) {
                val dialogListener = storeFragment as DialogListener
                dialogListener.onFinishEditDialog(editText.text.toString())
                dismiss()
            }
        })
    }
    override fun onResume() {
        super.onResume()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Hey", "onCreate")
        var  setFullScreen = false
        if (arguments != null) {
            setFullScreen = requireNotNull(arguments?.getBoolean("fullScreen"))
        }
        if (setFullScreen)
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
    interface DialogListener {
        fun onFinishEditDialog(inputText:String)
    }
}
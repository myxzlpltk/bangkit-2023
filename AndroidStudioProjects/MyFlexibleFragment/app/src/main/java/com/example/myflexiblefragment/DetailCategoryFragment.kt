package com.example.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class DetailCategoryFragment : Fragment(), View.OnClickListener {

    lateinit var tvCategoryName: TextView
    lateinit var tvCategoryDescription: TextView
    lateinit var btnProfile: Button
    lateinit var btnShowDialog: Button
    var description: String? = null

    companion object {
        var EXTRA_NAME = "extra_name"
        var EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCategoryName = view.findViewById(R.id.tv_category_name)
        tvCategoryDescription = view.findViewById(R.id.tv_category_description)
        btnProfile = view.findViewById(R.id.btn_profile)
        btnShowDialog = view.findViewById(R.id.btn_show_dialog)

        btnShowDialog.setOnClickListener(this)
        btnProfile.setOnClickListener(this)

        if (savedInstanceState != null) {
            description = savedInstanceState.getString(EXTRA_DESCRIPTION)
        }

        if (arguments != null) {
            tvCategoryName.text = arguments?.getString(EXTRA_NAME)
            tvCategoryDescription.text = description
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_show_dialog) {
            val mOptionDialogFragment = OptionDialogFragment()
            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(
                mFragmentManager, OptionDialogFragment::class.java.simpleName
            )
        } else if (v.id == R.id.btn_profile) {
            val mIntent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntent)
        }
    }

    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener =
        object : OptionDialogFragment.OnOptionDialogListener {
            override fun onOptionChosen(text: String?) {
                Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
            }
        }
}
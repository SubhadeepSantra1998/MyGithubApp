package com.subha.myusergithubapp.presentation.add_repo_screen

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.couponapp.presentation.helper.viewBinding
import com.subha.myusergithubapp.R
import com.subha.myusergithubapp.core.base.BaseBottomSheet
import com.subha.myusergithubapp.core.helper.Resource
import com.subha.myusergithubapp.data.models.CreateRepoRequest
import com.subha.myusergithubapp.databinding.AccesstokenBottomsheetBinding
import com.subha.myusergithubapp.databinding.CustomviewlayoutBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AccessTokenBottomSheet(name: String, description: String) : BaseBottomSheet() {


    private val reponame = name
    private val description = description

    private val binding by viewBinding(AccesstokenBottomsheetBinding::bind)
    private val viewModel: CreateRepoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.accesstoken_bottomsheet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()

    }

    private fun setUI() {
        binding.btnyes.setOnClickListener {
            var token = binding.etToken.text.toString().trim()
            if (TextUtils.isEmpty(token)) {
                Toast.makeText(requireContext(), "Token cannot be empty !", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val request = CreateRepoRequest(name = reponame, description = description)
                createRepository("Bearer $token", request)
            }
        }
        binding.btncancel.setOnClickListener {
            dismiss()
        }
    }


    private fun createRepository(token: String, request: CreateRepoRequest) {
        viewModel.createRepository(token, request)
        viewModel.createRepoLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    closeProgress()
                    dismiss()
                    successPopup()

                }
                is Resource.Error -> {
                    closeProgress()
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }

    private fun successPopup() {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog).create()
        val view = layoutInflater.inflate(R.layout.customviewlayout, null)
        var bind = CustomviewlayoutBinding.bind(view)
        builder.setView(view)
        builder.setCancelable(true)
        builder.show()
    }

}
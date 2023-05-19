package com.msaifurrijaal.tokoonline.presentation.user

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.msaifurrijaal.tokoonline.R
import com.msaifurrijaal.tokoonline.data.hawkstorage.HawkStorage
import com.msaifurrijaal.tokoonline.data.model.Resource
import com.msaifurrijaal.tokoonline.databinding.FragmentUserBinding
import com.msaifurrijaal.tokoonline.presentation.login.LoginActivity
import com.msaifurrijaal.tokoonline.presentation.main.MainActivity
import com.msaifurrijaal.tokoonline.utils.showDialogError
import com.msaifurrijaal.tokoonline.utils.showDialogLoading
import com.msaifurrijaal.tokoonline.utils.showDialogNotification
import com.msaifurrijaal.tokoonline.utils.startActivity

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var dialogLoading: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogLoading = showDialogLoading(requireContext())
        getDataProfile()

        onAction()
    }

    private fun getDataProfile() {
        val user = HawkStorage.instance(context).getUser()
        val token = user.accessToken
        userViewModel.getProfile(token).observe(viewLifecycleOwner){ state ->
            when(state){
                Resource.Empty -> {
                    dialogLoading.dismiss()
                    showDialogNotification(requireContext(), "EMPTY")
                }
                is Resource.Error -> {
                    dialogLoading.dismiss()
                    val errorMessage = state.errorMessage
                    showDialogError(requireContext(),errorMessage)
                }
                Resource.Loading -> dialogLoading.show()
                is Resource.Success -> {
                    val data = state.data

                    val imageUrl = "https://ui-avatars.com/api/?name=${data.name}2&background=388E3C&color=ffffff&size=128"
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(android.R.color.darker_gray)
                        .into(binding.ivAvatarUser)

                    binding.tvNameUser.text = data.name
                    binding.tvEmailUser.text = data.email
                    binding.tvPhoneUser.text = data.phone

                    dialogLoading.dismiss()
                }
            }
        }
    }

    private fun onAction() {
        binding.btnLogoutUser.setOnClickListener {
            HawkStorage.instance(requireContext()).deleteAll()
            (activity as MainActivity).startActivity<LoginActivity>()
            (activity as MainActivity).finishAffinity()
        }

        binding.btnChangeLanguageUser.setOnClickListener {
            val intent = Intent(ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
    }
}
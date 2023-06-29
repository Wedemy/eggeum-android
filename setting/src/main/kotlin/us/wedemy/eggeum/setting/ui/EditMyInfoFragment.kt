/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.setting.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.common.extension.repeatOnStarted
import us.wedemy.eggeum.common.extension.textChangesAsFlow
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.setting.R
import us.wedemy.eggeum.setting.databinding.FragmentEditMyInfoBinding
import us.wedemy.eggeum.setting.viewmodel.EditMyInfoViewModel

@AndroidEntryPoint
class EditMyInfoFragment : BaseFragment<FragmentEditMyInfoBinding>() {
  override fun getViewBinding() = FragmentEditMyInfoBinding.inflate(layoutInflater)

  private val viewModel by viewModels<EditMyInfoViewModel>()

  private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
    if (uri != null) {
      viewModel.setProfileImageUri(uri.toString())
    } else {
      Timber.tag("PhotoPicker").d("No media selected")
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    binding.tbEditMyInfo.setNavigationOnClickListener {
      if (!findNavController().navigateUp()) {
        requireActivity().finish()
      }
    }

    binding.ivEditMyInfoProfile.setOnClickListener {
      pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    binding.btnEditMyInfo.setOnClickListener {
      requireActivity().finish()
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.profileImageUri.collect { uri ->
          if (uri.isNotEmpty()) {
            binding.ivEditMyInfoProfile.load(uri) {
              crossfade(true)
              placeholder(us.wedemy.eggeum.design.R.drawable.ic_profile_filled_80)
            }
          } else {
            binding.ivEditMyInfoProfile.setImageResource(us.wedemy.eggeum.design.R.drawable.ic_profile_filled_80)
          }
        }
      }

      launch {
        val editTextFlow = binding.tietEditNickname.textChangesAsFlow()
        editTextFlow.collect { text ->
          val nickname = text.toString().trim()
          viewModel.handleNicknameValidation(nickname)
        }
      }

      launch {
        viewModel.inputNicknameState.collect { state ->
          when (state) {
            EditTextState.Idle -> clearError()
            is EditTextState.Success -> setValidState()
            is EditTextState.Error -> state.stringRes?.let(::setErrorMessage)
          }
          binding.btnEditMyInfo.isEnabled = state == EditTextState.Success
        }
      }
    }
  }

  private fun clearError() {
    binding.tilEditNickname.apply {
      error = null
      endIconDrawable = null
    }
  }

  private fun setErrorMessage(stringRes: Int) {
    when (stringRes) {
      R.string.empty_error_text -> setEmptyError()
      else -> setMinLengthError()
    }
  }

  private fun setEmptyError() {
    binding.tilEditNickname.apply {
      error = getString(R.string.empty_error_text)
      endIconDrawable = null
    }
  }

  private fun setMinLengthError() {
    binding.tilEditNickname.apply {
      error = getString(R.string.min_length_error_text)
      setEndIconDrawable(us.wedemy.eggeum.design.R.drawable.ic_x_filled_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.design.R.color.gray_400)
      setEndIconTintList(ColorStateList.valueOf(color))
      setEndIconOnClickListener {
        binding.tietEditNickname.text?.clear()
      }
    }
  }

  private fun setValidState() {
    binding.tilEditNickname.apply {
      error = null
      setEndIconDrawable(us.wedemy.eggeum.design.R.drawable.ic_check_colored_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.design.R.color.teal_500)
      setEndIconTintList(ColorStateList.valueOf(color))
      boxStrokeColor = color
      setEndIconOnClickListener {}
    }
  }
}

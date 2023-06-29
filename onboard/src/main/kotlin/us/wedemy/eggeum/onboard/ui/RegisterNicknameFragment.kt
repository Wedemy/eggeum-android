/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.common.extension.repeatOnStarted
import us.wedemy.eggeum.common.extension.textChangesAsFlow
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.onboard.R
import us.wedemy.eggeum.onboard.databinding.FragmentRegisterNicknameBinding
import us.wedemy.eggeum.onboard.viewmodel.RegisterNicknameViewModel

@AndroidEntryPoint
class RegisterNicknameFragment : BaseFragment<FragmentRegisterNicknameBinding>() {
  override fun getViewBinding() = FragmentRegisterNicknameBinding.inflate(layoutInflater)

  private val viewModel by viewModels<RegisterNicknameViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    binding.tbRegisterNickname.setNavigationOnClickListener {
      if (!findNavController().navigateUp()) {
        requireActivity().finish()
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        val editTextFlow = binding.tietRegisterNickname.textChangesAsFlow()
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
          binding.btnRegisterNickname.isEnabled = state == EditTextState.Success
        }
      }
    }
  }

  private fun clearError() {
    binding.tilRegisterNickname.apply {
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
    binding.tilRegisterNickname.apply {
      error = getString(R.string.empty_error_text)
      endIconDrawable = null
    }
  }

  private fun setMinLengthError() {
    binding.tilRegisterNickname.apply {
      error = getString(R.string.min_length_error_text)
      setEndIconDrawable(us.wedemy.eggeum.design.R.drawable.ic_x_filled_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.design.R.color.gray_400)
      setEndIconTintList(ColorStateList.valueOf(color))
      setEndIconOnClickListener {
        binding.tietRegisterNickname.text?.clear()
      }
    }
  }

  private fun setValidState() {
    binding.tilRegisterNickname.apply {
      error = null
      setEndIconDrawable(us.wedemy.eggeum.design.R.drawable.ic_check_colored_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.design.R.color.teal_500)
      setEndIconTintList(ColorStateList.valueOf(color))
      boxStrokeColor = color
      setEndIconOnClickListener {}
    }
  }
}

/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:OptIn(FlowPreview::class)

package us.wedemy.eggeum.android.onboard.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.onboard.R
import us.wedemy.eggeum.android.onboard.databinding.FragmentRegisterNicknameBinding
import us.wedemy.eggeum.android.onboard.viewmodel.OnBoardViewModel

@AndroidEntryPoint
class RegisterNicknameFragment : BaseFragment<FragmentRegisterNicknameBinding>() {
  override fun getViewBinding() = FragmentRegisterNicknameBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<OnBoardViewModel>()

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
    binding.btnRegisterNickname.setOnClickListener {
      viewModel.signUp()
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        val editTextFlow = binding.tietRegisterNickname.textChangesAsFlow()
        editTextFlow
          .debounce(300L)
          .collect { text ->
            val nickname = text.toString().trim()
            viewModel.handleNicknameValidation(nickname)
          }
      }

      launch {
        viewModel.nicknameState.collect { state ->
          when (state) {
            is EditTextState.Idle -> clearError()
            is EditTextState.Success -> setValid()
            is EditTextState.Error -> setError(state.error)
          }
          binding.btnRegisterNickname.isEnabled = state == EditTextState.Success
        }
      }

      launch {
        viewModel.navigateToMainEvent.collect {
          Toast.makeText(requireContext(), getString(R.string.sign_up_complete), Toast.LENGTH_SHORT).show()
          (activity as OnboardActivity).navigateToMain()
        }
      }

      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message.asString(requireContext()), Toast.LENGTH_SHORT).show()
        }
      }

      launch {
        viewModel.navigateToLoginEvent.collect {
          (activity as OnboardActivity).navigateToLogin()
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

  private fun setError(error: TextInputError) {
    when (error) {
      TextInputError.EMPTY -> setEmptyTextError()
      TextInputError.TOO_SHORT -> setTooShortTextError()
      TextInputError.ALREADY_EXIST -> setAlreadyExistTextError()
      TextInputError.CONTAINS_WHITESPACE -> setContainsWhitespaceError()
      TextInputError.INVALID_CHARACTER -> setContainsInvalidCharacterError()
    }
  }

  private fun setEmptyTextError() {
    binding.tilRegisterNickname.apply {
      error = getString(R.string.empty_text_error)
      endIconDrawable = null
    }
  }

  private fun setTooShortTextError() {
    binding.tilRegisterNickname.apply {
      error = getString(R.string.too_short_text_error)
      setEndIconDrawable(us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400)
      setEndIconTintList(ColorStateList.valueOf(color))
      setEndIconOnClickListener {
        binding.tietRegisterNickname.text?.clear()
        viewModel.setNickname("")
      }
    }
  }

  private fun setAlreadyExistTextError() {
    binding.tilRegisterNickname.apply {
      error = getString(R.string.already_exist_text_error)
      setEndIconDrawable(us.wedemy.eggeum.android.design.R.drawable.ic_close_filled_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400)
      setEndIconTintList(ColorStateList.valueOf(color))
      setEndIconOnClickListener {
        binding.tietRegisterNickname.text?.clear()
        viewModel.setNickname("")
      }
    }
  }

  private fun setContainsWhitespaceError() {
    binding.tilRegisterNickname.apply {
      error = getString(R.string.contains_whitespace_error)
      setEndIconDrawable(us.wedemy.eggeum.android.design.R.drawable.ic_close_filled_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400)
      setEndIconTintList(ColorStateList.valueOf(color))
      setEndIconOnClickListener {
        binding.tietRegisterNickname.text?.clear()
        viewModel.setNickname("")
      }
    }
  }

  private fun setContainsInvalidCharacterError() {
    binding.tilRegisterNickname.apply {
      error = getString(R.string.contains_invalid_character_error)
      setEndIconDrawable(us.wedemy.eggeum.android.design.R.drawable.ic_close_filled_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400)
      setEndIconTintList(ColorStateList.valueOf(color))
      setEndIconOnClickListener {
        binding.tietRegisterNickname.text?.clear()
        viewModel.setNickname("")
      }
    }
  }

  private fun setValid() {
    binding.tilRegisterNickname.apply {
      error = null
      setEndIconDrawable(us.wedemy.eggeum.android.design.R.drawable.ic_check_colored_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.teal_500)
      setEndIconTintList(ColorStateList.valueOf(color))
      boxStrokeColor = color
    }
  }
}

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
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import us.wedemy.eggeum.onboard.R
import us.wedemy.eggeum.onboard.databinding.FragmentRegisterNicknameBinding
import us.wedemy.eggeum.onboard.ui.base.BaseFragment
import us.wedemy.eggeum.onboard.util.ViewModelFactory
import us.wedemy.eggeum.onboard.util.repeatOnStarted
import us.wedemy.eggeum.onboard.util.textChangesToFlow
import us.wedemy.eggeum.onboard.viewmodel.RegisterNicknameViewModel

class RegisterNicknameFragment : BaseFragment<FragmentRegisterNicknameBinding>(R.layout.fragment_register_nickname) {

  override fun getViewBinding() = FragmentRegisterNicknameBinding.inflate(layoutInflater)

  private lateinit var viewModel: RegisterNicknameViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val savedStateHandle = SavedStateHandle()
    val viewModelFactory = ViewModelFactory(savedStateHandle)
    viewModel = ViewModelProvider(this, viewModelFactory)[RegisterNicknameViewModel::class.java]

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
      val editTextFlow = binding.tietRegisterNickname.textChangesToFlow()
      editTextFlow
        .onEach { text ->
          handleNicknameValidation(text)
        }
        .launchIn(this)

      launch {
        viewModel.enableRegisterNickname.collect {
          binding.btnRegisterNickname.isEnabled = it
        }
      }
    }
  }

  private fun handleNicknameValidation(text: CharSequence?) {
    text?.let {
      val nickname = it.toString().trim()
      when {
        nickname.isEmpty() -> {
          setEmptyError()
        }
        nickname.length < 2 -> {
          setMinLengthError()
          viewModel.setButtonState(false)
        }
        else -> {
          setValidState()
          viewModel.setNickname(nickname)
          viewModel.setButtonState(true)
        }
      }
    }
  }

  private fun setEmptyError() {
    binding.tilRegisterNickname.apply {
      error = context.getString(R.string.empty_error_text)
      endIconDrawable = null
    }
  }

  private fun setMinLengthError() {
    binding.tilRegisterNickname.apply {
      error = context.getString(R.string.min_length_error_text)
      setEndIconDrawable(us.wedemy.eggeum.design.R.drawable.ic_x_filled_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.design.R.color.gray_400)
      setEndIconTintList(ColorStateList.valueOf(color))
    }
  }

  private fun setValidState() {
    binding.tilRegisterNickname.apply {
      error = null
      setEndIconDrawable(us.wedemy.eggeum.design.R.drawable.ic_check_colored_16)
      val color = ContextCompat.getColor(requireContext(), us.wedemy.eggeum.design.R.color.teal_500)
      setEndIconTintList(ColorStateList.valueOf(color))
      boxStrokeColor = color
    }
  }
}
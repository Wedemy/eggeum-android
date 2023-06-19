/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.register_cafe.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import us.wedemy.eggeum.common.ui.base.BaseFragment
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.common.util.repeatOnStarted
import us.wedemy.eggeum.common.util.safeNavigate
import us.wedemy.eggeum.common.util.textChangesToFlow
import us.wedemy.eggeum.register_cafe.R
import us.wedemy.eggeum.register_cafe.adapter.CafeImageAdapter
import us.wedemy.eggeum.register_cafe.databinding.FragmentRegisterCafeBinding
import us.wedemy.eggeum.register_cafe.item.CafeImageItem
import us.wedemy.eggeum.register_cafe.viewmodel.RegisterCafeViewModel
import us.wedemy.eggeum.register_cafe.viewmodel.RegisterCafeViewModelFactory

class RegisterCafeFragment : BaseFragment<FragmentRegisterCafeBinding>(R.layout.fragment_register_cafe) {

  override fun getViewBinding() = FragmentRegisterCafeBinding.inflate(layoutInflater)

  private lateinit var viewModel: RegisterCafeViewModel
  private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

  private val cafeImageAdapter by lazy {
    CafeImageAdapter()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    val savedStateHandle = SavedStateHandle()
    val viewModelFactory = RegisterCafeViewModelFactory(savedStateHandle)
    viewModel = ViewModelProvider(this, viewModelFactory)[RegisterCafeViewModel::class.java]

    pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
      if (uris.isNotEmpty()) {
        Log.d("PhotoPicker", "Number of items selected: ${uris.size}")
        binding.tvRegisterCafePictureNumber.text = "${uris.size}/10"
        val imageItems = uris.map { CafeImageItem(it.toString()) }
        cafeImageAdapter.submitList(imageItems)
      } else {
        Log.d("PhotoPicker", "No media selected")
      }
    }

    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    binding.apply {
      rvCafeImage.apply {
        setHasFixedSize(true)
        adapter = cafeImageAdapter
      }
    }
  }

  private fun initListener() {
    with(binding) {
      tbRegisterCafe.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      binding.clRegisterCafePicture.setOnClickListener {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
      }

      binding.btnRegisterCafe.setOnClickListener {
        val action = RegisterCafeFragmentDirections.actionRegisterCafeFragmentToRegisterCafeCompleteFragment()
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      val cafeNameEditTextFlow = binding.tietRegisterCafeName.textChangesToFlow()
      cafeNameEditTextFlow
        .onEach { text ->
          val cafeName = text.toString().trim()
          viewModel.handleCafeNameValidation(cafeName)
        }
        .launchIn(this)

      val cafeAddressEditTextFlow = binding.tietRegisterCafeAddress.textChangesToFlow()
      cafeAddressEditTextFlow
        .onEach { text ->
          val cafeAddress = text.toString().trim()
          viewModel.handleCafeAddressValidation(cafeAddress)
        }
        .launchIn(this)

      launch {
        viewModel.inputCafeNameState.collect { state ->
          when (state) {
            is EditTextState.Idle -> {
              clearError(binding.tilRegisterCafeName)
            }

            is EditTextState.Error -> {
              setEmptyError(binding.tilRegisterCafeName)
            }

            is EditTextState.Success -> {
              setValidState(binding.tilRegisterCafeName)
            }
          }
        }
      }

      launch {
        viewModel.inputCafeAddressState.collect { state ->
          when (state) {
            is EditTextState.Idle -> {
              clearError(binding.tilRegisterCafeAddress)
            }

            is EditTextState.Error -> {
              setEmptyError(binding.tilRegisterCafeAddress)
            }

            is EditTextState.Success -> {
              setValidState(binding.tilRegisterCafeAddress)
            }
          }
        }
      }

      launch {
        viewModel.enableRegisterCafe.collect { state ->
          binding.btnRegisterCafe.isEnabled = state
        }
      }
    }
  }

  private fun clearError(textInputLayout: TextInputLayout) {
    textInputLayout.apply {
      error = null
    }
  }

  private fun setEmptyError(textInputLayout: TextInputLayout) {
    textInputLayout.apply {
      error = "temp"
    }
  }

  private fun setValidState(textInputLayout: TextInputLayout) {
    textInputLayout.apply {
      error = null
    }
  }
}
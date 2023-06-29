/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.registercafe.ui

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.common.extension.repeatOnStarted
import us.wedemy.eggeum.common.extension.safeNavigate
import us.wedemy.eggeum.common.extension.textChangesAsFlow
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.registercafe.R
import us.wedemy.eggeum.registercafe.adapter.CafeImageAdapter
import us.wedemy.eggeum.registercafe.databinding.FragmentRegisterCafeBinding
import us.wedemy.eggeum.registercafe.item.CafeImageItem
import us.wedemy.eggeum.registercafe.viewmodel.RegisterCafeViewModel
import us.wedemy.eggeum.registercafe.viewmodel.RegisterCafeViewModelFactory

class RegisterCafeFragment : BaseFragment<FragmentRegisterCafeBinding>() {
  override fun getViewBinding() = FragmentRegisterCafeBinding.inflate(layoutInflater)

  private lateinit var viewModel: RegisterCafeViewModel

  private var pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
    if (uris.isNotEmpty()) {
      val imageItems = uris.map { CafeImageItem(it.toString()) }
      viewModel.setCafeImages(imageItems)
    } else {
      Timber.tag("PhotoPicker").d("No media selected")
    }
  }

  private val cafeImageAdapter by lazy {
    CafeImageAdapter { position -> viewModel.deleteCafeImage(position) }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val savedStateHandle = SavedStateHandle()
    val viewModelFactory = RegisterCafeViewModelFactory(savedStateHandle)
    viewModel = ViewModelProvider(this, viewModelFactory)[RegisterCafeViewModel::class.java]

    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    binding.rvCafeImage.apply {
      setHasFixedSize(true)
      adapter = cafeImageAdapter
    }
  }

  private fun initListener() {
    with(binding) {
      tbRegisterCafe.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      clRegisterCafeImage.setOnClickListener {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
      }

      btnRegisterCafe.setOnClickListener {
        val action = RegisterCafeFragmentDirections.actionRegisterCafeFragmentToRegisterCafeCompleteFragment()
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeImages.collect { cafeImages ->
          cafeImageAdapter.submitList(cafeImages)
          binding.tvRegisterCafeImageNumber.text = getString(R.string.cafe_image_number, cafeImages.size.toString())

          if (cafeImages.isEmpty()) {
            binding.tvRegisterCafeImageLimit.setTextColor(
              ContextCompat.getColor(
                requireActivity(),
                us.wedemy.eggeum.design.R.color.error_500,
              ),
            )
          } else {
            binding.tvRegisterCafeImageLimit.setTextColor(
              ContextCompat.getColor(
                requireActivity(),
                us.wedemy.eggeum.design.R.color.gray_400,
              ),
            )
          }
        }
      }

      launch {
        val cafeNameEditTextFlow = binding.tietRegisterCafeName.textChangesAsFlow()
        cafeNameEditTextFlow.collect { text ->
          val cafeName = text.toString().trim()
          viewModel.handleCafeNameValidation(cafeName)
        }
      }

      launch {
        val cafeAddressEditTextFlow = binding.tietRegisterCafeAddress.textChangesAsFlow()
        cafeAddressEditTextFlow.collect { text ->
          val cafeAddress = text.toString().trim()
          viewModel.handleCafeAddressValidation(cafeAddress)
        }
      }

      launch {
        viewModel.inputCafeNameState.collect { state ->
          when (state) {
            is EditTextState.Idle -> clearError(binding.tilRegisterCafeName)
            is EditTextState.Error -> setEmptyError(binding.tilRegisterCafeName)
            is EditTextState.Success -> setValidState(binding.tilRegisterCafeName)
          }
        }
      }

      launch {
        viewModel.inputCafeAddressState.collect { state ->
          when (state) {
            is EditTextState.Idle -> clearError(binding.tilRegisterCafeAddress)
            is EditTextState.Error -> setEmptyError(binding.tilRegisterCafeAddress)
            is EditTextState.Success -> setValidState(binding.tilRegisterCafeAddress)
          }
        }
      }

      launch {
        viewModel.enableRegisterCafe.collect { state ->
          binding.tvPleaseToInputAllRequiredItem.isInvisible = state
          binding.btnRegisterCafe.isEnabled = state
        }
      }
    }
  }

  private fun clearError(textInputLayout: TextInputLayout) {
    textInputLayout.error = null
  }

  private fun setEmptyError(textInputLayout: TextInputLayout) {
    textInputLayout.error = " "
  }

  private fun setValidState(textInputLayout: TextInputLayout) {
    textInputLayout.error = null
  }
}

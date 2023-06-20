/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.registercafe.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
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
import us.wedemy.eggeum.registercafe.databinding.FragmentRegisterCafeBinding
import us.wedemy.eggeum.registercafe.R
import us.wedemy.eggeum.registercafe.adapter.CafeImageAdapter
import us.wedemy.eggeum.registercafe.item.CafeImageItem
import us.wedemy.eggeum.registercafe.viewmodel.RegisterCafeViewModel
import us.wedemy.eggeum.registercafe.viewmodel.RegisterCafeViewModelFactory

class RegisterCafeFragment : BaseFragment<FragmentRegisterCafeBinding>(R.layout.fragment_register_cafe) {

  override fun getViewBinding() = FragmentRegisterCafeBinding.inflate(layoutInflater)

  private lateinit var viewModel: RegisterCafeViewModel
  private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

  private val cafeImageItemClickListener = CafeImageItemClickListener(
    onDeleteClick = { position -> viewModel.deleteCafeImage(position) },
  )

  private val cafeImageAdapter by lazy {
    CafeImageAdapter(cafeImageItemClickListener)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val savedStateHandle = SavedStateHandle()
    val viewModelFactory = RegisterCafeViewModelFactory(savedStateHandle)
    viewModel = ViewModelProvider(this, viewModelFactory)[RegisterCafeViewModel::class.java]

    pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
      if (uris.isNotEmpty()) {
        val imageItems = uris.map { CafeImageItem(it.toString()) }
        viewModel.setCafeImages(imageItems)
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

      binding.clRegisterCafeImage.setOnClickListener {
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
      launch {
        viewModel.cafeImages.collect {
          cafeImageAdapter.submitList(it)
          binding.tvRegisterCafeImageNumber.text = getString(R.string.cafe_image_number, it.size.toString())

          if (it.isEmpty()) {
            binding.tvRegisterCafeImageLimit.setTextColor(
              ContextCompat.getColor(
                requireContext(),
                us.wedemy.eggeum.design.R.color.error_500,
              ),
            )
          } else {
            binding.tvRegisterCafeImageLimit.setTextColor(
              ContextCompat.getColor(
                requireContext(),
                us.wedemy.eggeum.design.R.color.gray_400,
              ),
            )
          }
        }
      }

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
          binding.tvPleaseToInputAllRequiredItem.isInvisible = state
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
    textInputLayout.error = " "
  }

  private fun setValidState(textInputLayout: TextInputLayout) {
    textInputLayout.apply {
      error = null
    }
  }
}

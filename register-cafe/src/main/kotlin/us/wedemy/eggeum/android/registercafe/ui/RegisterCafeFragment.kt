/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.registercafe.R
import us.wedemy.eggeum.android.registercafe.adapter.CafeImageAdapter
import us.wedemy.eggeum.android.registercafe.adapter.listener.CafeImageClickListener
import us.wedemy.eggeum.android.registercafe.databinding.FragmentRegisterCafeBinding
import us.wedemy.eggeum.android.registercafe.model.CafeImageModel
import us.wedemy.eggeum.android.registercafe.viewmodel.RegisterCafeViewModel

@AndroidEntryPoint
class RegisterCafeFragment : BaseFragment<FragmentRegisterCafeBinding>() {
  override fun getViewBinding() = FragmentRegisterCafeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<RegisterCafeViewModel>()

  private var pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
    if (uris.isNotEmpty()) {
      val imageItems = uris.map { CafeImageModel(it.toString()) }
      viewModel.setCafeImages(imageItems)
    } else {
      Timber.tag("PhotoPicker").d("No media selected")
    }
  }

  private val cafeImageAdapter by lazy {
    CafeImageAdapter(object : CafeImageClickListener {
      override fun onItemClick(position: Int) {
        viewModel.deleteCafeImage(position)
      }
    })
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initView()
    initListener()
    initDataObserver()
    initEventObserver()
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
        viewModel.registerCafe()
      }
    }
  }

  private fun initDataObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeImages.collect { cafeImages ->
          cafeImageAdapter.submitList(cafeImages)
          binding.tvRegisterCafeImageNumber.text = getString(R.string.cafe_image_number, cafeImages.size.toString())

          if (cafeImages.isEmpty()) {
            binding.tvRegisterCafeImageLimit.setTextColor(
              ContextCompat.getColor(
                requireActivity(),
                us.wedemy.eggeum.android.design.R.color.error_500,
              ),
            )
          } else {
            binding.tvRegisterCafeImageLimit.setTextColor(
              ContextCompat.getColor(
                requireActivity(),
                us.wedemy.eggeum.android.design.R.color.gray_400,
              ),
            )
          }
        }
      }

      collectTextChanges(this, binding.tietRegisterCafeName) { cafeName ->
        viewModel.handleCafeNameValidation(cafeName)
      }

      collectTextChanges(this, binding.tietRegisterCafeAddress) { cafeAddress ->
        viewModel.handleCafeAddressValidation(cafeAddress)
      }

      launch {
        viewModel.cafeNameState.collect { state ->
          when (state) {
            is EditTextState.Idle -> clearError(binding.tilRegisterCafeName)
            is EditTextState.Error -> setError(binding.tilRegisterCafeName)
            is EditTextState.Success -> setValid(binding.tilRegisterCafeName)
          }
        }
      }

      launch {
        viewModel.cafeAddressState.collect { state ->
          when (state) {
            is EditTextState.Idle -> clearError(binding.tilRegisterCafeAddress)
            is EditTextState.Error -> setError(binding.tilRegisterCafeAddress)
            is EditTextState.Success -> setValid(binding.tilRegisterCafeAddress)
          }
        }
      }

      collectTextChanges(this, binding.tietRegisterCafeArea) { cafeArea ->
        viewModel.setCafeArea(cafeArea)
      }

      collectTextChanges(this, binding.tietRegisterCafeMeetingRoom) { cafeMeetingRoom ->
        viewModel.setCafeMeetingRoom(cafeMeetingRoom)
      }

      collectTextChanges(this, binding.tietRegisterCafeMultiSeat) { cafeMultiSeat ->
        viewModel.setCafeMultiSeat(cafeMultiSeat)
      }

      collectTextChanges(this, binding.tietRegisterCafeMultiSeat) { cafeSingleSeat ->
        viewModel.setCafeSingleSeat(cafeSingleSeat)
      }

      collectTextChanges(this, binding.tietRegisterCafeBusinessHours) { cafeBusinessHours ->
        viewModel.setCafeBusinessHours(cafeBusinessHours)
      }

      collectTextChanges(this, binding.tietRegisterCafeRestRoom) { cafeRestRoom ->
        viewModel.setCafeRestRoom(cafeRestRoom)
      }

      collectTextChanges(this, binding.tietRegisterCafeParking) { cafeParking ->
        viewModel.setCafeParking(cafeParking)
      }

      collectTextChanges(this, binding.tietRegisterCafeSmokeArea) { cafeSmokeArea ->
        viewModel.setCafeSmokeArea(cafeSmokeArea)
      }

      collectTextChanges(this, binding.tietRegisterCafeWifi) { cafeWifi ->
        viewModel.setCafeWifi(cafeWifi)
      }

      collectTextChanges(this, binding.tietRegisterCafeOutlet) { cafeOutlet ->
        viewModel.setCafeOutlet(cafeOutlet)
      }

      collectTextChanges(this, binding.tietRegisterCafeMobileCharging) { cafeMobileCharging ->
        viewModel.setCafeMobileCharging(cafeMobileCharging)
      }

      collectTextChanges(this, binding.tietRegisterCafePhone) { cafePhone ->
        viewModel.setCafePhone(cafePhone)
      }

      collectTextChanges(this, binding.tietRegisterCafeBlogUri) { cafeBlogUri ->
        viewModel.setCafeBlogUri(cafeBlogUri)
      }

      collectTextChanges(this, binding.tietRegisterCafeInstagramUri) { cafeInstagramUri ->
        viewModel.setCafeInstagramUri(cafeInstagramUri)
      }

      collectTextChanges(this, binding.tietRegisterCafeWebsiteUri) { cafeWebsiteUri ->
        viewModel.setCafeWebsiteUri(cafeWebsiteUri)
      }
    }
  }

  private fun initEventObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToRegisterCafeCompleteEvent.collect {
          val action = RegisterCafeFragmentDirections.actionFragmentRegisterCafeToFragmentRegisterCafeComplete()
          findNavController().safeNavigate(action)
        }
      }

      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message.asString(requireContext()), Toast.LENGTH_SHORT).show()
        }
      }

      launch {
        viewModel.enableRegisterCafe.collect { flag ->
          binding.tvPleaseToInputAllRequiredItem.isInvisible = flag
          binding.btnRegisterCafe.isEnabled = flag
        }
      }

      launch {
        viewModel.navigateToLoginEvent.collect {
          (activity as RegisterCafeActivity).navigateToLogin()
        }
      }
    }
  }

  private fun collectTextChanges(scope: CoroutineScope, editText: EditText, onTextChange: (String) -> Unit) {
    scope.launch {
      editText.textChangesAsFlow()
        .collect { text ->
          onTextChange(text.toString().trim())
        }
    }
  }

  private fun clearError(textInputLayout: TextInputLayout) {
    textInputLayout.error = null
  }

  private fun setError(textInputLayout: TextInputLayout) {
    textInputLayout.error = " "
  }

  private fun setValid(textInputLayout: TextInputLayout) {
    textInputLayout.error = null
  }
}

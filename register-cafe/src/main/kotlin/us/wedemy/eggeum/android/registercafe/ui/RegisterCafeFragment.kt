/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.registercafe.R
import us.wedemy.eggeum.android.registercafe.adapter.CafeImageAdapter
import us.wedemy.eggeum.android.registercafe.adapter.listener.CafeImageClickListener
import us.wedemy.eggeum.android.registercafe.databinding.FragmentRegisterCafeBinding
import us.wedemy.eggeum.android.registercafe.model.CafeImageItem
import us.wedemy.eggeum.android.registercafe.viewmodel.RegisterCafeViewModel

@AndroidEntryPoint
class RegisterCafeFragment : BaseFragment<FragmentRegisterCafeBinding>() {
  override fun getViewBinding() = FragmentRegisterCafeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<RegisterCafeViewModel>()

  private var pickMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris ->
    if (uris.isNotEmpty()) {
      val imageItems = uris.map { CafeImageItem(it.toString()) }
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
        viewModel.registerCafe()
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToRegisterCafeCompleteEvent.collect {
          val action = RegisterCafeFragmentDirections.actionFragmentRegisterCafeToFragmentRegisterCafeComplete()
          findNavController().safeNavigate(action)
        }
      }

      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
      }

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

      launch {
        viewModel.enableRegisterCafe.collect { flag ->
          binding.tvPleaseToInputAllRequiredItem.isInvisible = flag
          binding.btnRegisterCafe.isEnabled = flag
        }
      }

      launch {
        val cafeAreaEditTextFlow = binding.tietRegisterCafeArea.textChangesAsFlow()
        cafeAreaEditTextFlow.collect { text ->
          val cafeArea = text.toString().trim()
          viewModel.setCafeArea(cafeArea)
        }
      }

      launch {
        val cafeMeetingRoomEditTextFlow = binding.tietRegisterCafeMeetingRoom.textChangesAsFlow()
        cafeMeetingRoomEditTextFlow.collect { text ->
          val cafeMeetingRoom = text.toString().trim()
          viewModel.setCafeMeetingRoom(cafeMeetingRoom)
        }
      }

      launch {
        val cafeMultiSeatEditTextFlow = binding.tietRegisterCafeMultiSeat.textChangesAsFlow()
        cafeMultiSeatEditTextFlow.collect { text ->
          val cafeMultiSeat = text.toString().trim()
          viewModel.setCafeMultiSeat(cafeMultiSeat)
        }
      }

      launch {
        val cafeSingleSeatEditTextFlow = binding.tietRegisterCafeMultiSeat.textChangesAsFlow()
        cafeSingleSeatEditTextFlow.collect { text ->
          val cafeSingleSeat = text.toString().trim()
          viewModel.setCafeSingleSeat(cafeSingleSeat)
        }
      }

      launch {
        val cafeBusinessHoursEditTextFlow = binding.tietRegisterCafeBusinessHours.textChangesAsFlow()
        cafeBusinessHoursEditTextFlow.collect { text ->
          val cafeBusinessHours = text.toString().trim()
          viewModel.setCafeBusinessHours(cafeBusinessHours)
        }
      }

      launch {
        val cafeRestRoomEditTextFlow = binding.tietRegisterCafeRestRoom.textChangesAsFlow()
        cafeRestRoomEditTextFlow.collect { text ->
          val cafeRestRoom = text.toString().trim()
          viewModel.setCafeRestRoom(cafeRestRoom)
        }
      }

      launch {
        val cafeParkingEditTextFlow = binding.tietRegisterCafeParking.textChangesAsFlow()
        cafeParkingEditTextFlow.collect { text ->
          val cafeParking = text.toString().trim()
          viewModel.setCafeParking(cafeParking)
        }
      }

      launch {
        val cafeSmokeAreaEditTextFlow = binding.tietRegisterCafeSmokeArea.textChangesAsFlow()
        cafeSmokeAreaEditTextFlow.collect { text ->
          val cafeSmoke = text.toString().trim()
          viewModel.setCafeSmokeArea(cafeSmoke)
        }
      }

      launch {
        val cafeWifiEditTextFlow = binding.tietRegisterCafeWifi.textChangesAsFlow()
        cafeWifiEditTextFlow.collect { text ->
          val cafeWifi = text.toString().trim()
          viewModel.setCafeWifi(cafeWifi)
        }
      }

      launch {
        val cafeOutletEditTextFlow = binding.tietRegisterCafeOutlet.textChangesAsFlow()
        cafeOutletEditTextFlow.collect { text ->
          val cafeOutlet = text.toString().trim()
          viewModel.setCafeOutlet(cafeOutlet)
        }
      }

      launch {
        val cafeMobileChargingEditTextFlow = binding.tietRegisterCafeMobileCharging.textChangesAsFlow()
        cafeMobileChargingEditTextFlow.collect { text ->
          val cafeMobileCharging = text.toString().trim()
          viewModel.setCafeMobileCharging(cafeMobileCharging)
        }
      }

      launch {
        val cafePhoneEditTextFlow = binding.tietRegisterCafePhone.textChangesAsFlow()
        cafePhoneEditTextFlow.collect { text ->
          val cafePhone = text.toString().trim()
          viewModel.setCafePhone(cafePhone)
        }
      }

      launch {
        val cafeBlogUriEditTextFlow = binding.tietRegisterCafeBlogUri.textChangesAsFlow()
        cafeBlogUriEditTextFlow.collect { text ->
          val cafeBlogUri = text.toString().trim()
          viewModel.setCafeBlogUri(cafeBlogUri)
        }
      }

      launch {
        val cafeInstagramUriEditTextFlow = binding.tietRegisterCafeInstagramUri.textChangesAsFlow()
        cafeInstagramUriEditTextFlow.collect { text ->
          val cafeInstagramUri = text.toString().trim()
          viewModel.setCafeInstagramUri(cafeInstagramUri)
        }
      }

      launch {
        val cafeWebsiteUriEditTextFlow = binding.tietRegisterCafeWebsiteUri.textChangesAsFlow()
        cafeWebsiteUriEditTextFlow.collect { text ->
          val cafeWebsiteUri = text.toString().trim()
          viewModel.setCafeWebsiteUri(cafeWebsiteUri)
        }
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

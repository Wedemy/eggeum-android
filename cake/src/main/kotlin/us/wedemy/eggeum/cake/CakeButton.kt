/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.FrameLayout
import us.wedemy.eggeum.cake.databinding.CakeButtonBinding
import us.wedemy.eggeum.cake.util.layoutInflater

public class CakeButton @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {
  private val binding = CakeButtonBinding.inflate(context.layoutInflater, this, true)

  public var text: String
    set(value) {
      binding.cakeButtonText.text = value
    }
    get() = binding.cakeButtonText.text.toString()

  private var prevOnClickListener: OnClickListener? = null
  public var enable: Boolean = true
    set(value) {
      val background = resources.getDrawable(
        if (value) R.drawable.teal_ripple_shape_radius_8 else R.drawable.shape_radius_8,
        context.theme,
      )
      val tint = resources.getColor(if (value) R.color.teal_500 else R.color.gray_300, context.theme)

      binding.cakeButton.background = background
      binding.cakeButton.backgroundTintList = ColorStateList.valueOf(tint)

      super.setOnClickListener(if (value) prevOnClickListener else null)
      isFocusable = value
      isClickable = value

      field = value
    }

  init {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CakeButton)

    val text = typedArray.getString(R.styleable.CakeButton_text)
    this.text = text.orEmpty()

    val enable = typedArray.getBoolean(R.styleable.CakeButton_enable, false)
    this.enable = enable

    typedArray.recycle()
  }

  override fun setOnClickListener(clickListener: OnClickListener?) {
    prevOnClickListener = clickListener
    enable = enable // call setOnClickListener if needed
  }
}

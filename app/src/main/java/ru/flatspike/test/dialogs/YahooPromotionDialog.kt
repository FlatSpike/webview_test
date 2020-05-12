package ru.flatspike.test.dialogs

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.flatspike.test.databinding.DialogYahooBinding

class YahooPromotionDialog : DialogFragment() {

    private val binding: DialogYahooBinding by lazy {
        DialogYahooBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // make dialog window transparent (that the easiest way)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.root.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://yahoo.com")))
        }
    }
}
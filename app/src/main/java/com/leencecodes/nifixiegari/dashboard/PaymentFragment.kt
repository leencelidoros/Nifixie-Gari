package com.leencecodes.nifixiegari.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.leencecodes.nifixiegari.R
import com.leencecodes.nifixiegari.databinding.FragmentPayment2Binding
import com.leencecodes.nifixiegari.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPayment2Binding
    private lateinit var daraja: Daraja

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayment2Binding.inflate(inflater, container, false)
        val view = binding.root


        daraja = Daraja.with("EUgdo9I1XZRUC9THxDtECMo7QNo50Sb2", "nbGMY4XH4cm6bqEh", Env.SANDBOX,
            object : DarajaListener<AccessToken> {
                override fun onResult(result: AccessToken) {
                    Toast.makeText(requireContext(), result.access_token, Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: String?) {
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                }

            })

        binding.button.setOnClickListener {
            val phone = binding.editTextPhone.text.toString().trim()
            val amount = binding.amountEditText.text.toString()

            if (phone.isNullOrEmpty() || amount.isNullOrEmpty()){
                return@setOnClickListener
            }

            val lnmExpress = LNMExpress(
                "174379",
                "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                TransactionType.CustomerPayBillOnline,
                amount,
                phone,
                "174379",
                phone,
                "https://mycallback.com",
                "001ABC",
                "Goods Payment"
            )

            daraja.requestMPESAExpress(lnmExpress, object : DarajaListener<LNMResult> {
                override fun onResult(result: LNMResult) {
                    Toast.makeText(
                        requireContext(),
                        "Result: ${result.ResponseDescription}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(error: String?) {
                    Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }
}
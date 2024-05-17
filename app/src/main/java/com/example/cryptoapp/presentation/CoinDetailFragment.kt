package com.example.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    private val component by lazy {
        (requireActivity().application as CryptoApp).appModule.activityComponentFactory().create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
    }

    private var coinSymbol = UNDEFINED_SYMBOL
    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun observe() {
        viewModel.getDetailInfo(coinSymbol).observe(viewLifecycleOwner) {
            with(binding) {
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }

    }

    private fun parseArgs() {
        coinSymbol = requireArguments().getString(COIN_SYMBOL, UNDEFINED_SYMBOL)
    }

    companion object {
        private const val TAG = "CoinDetailFragment"
        private const val COIN_SYMBOL = "extra_coin_symbol"
        private const val UNDEFINED_SYMBOL = ""

        fun newInstance(coinSymbol: String): CoinDetailFragment {
            val args = Bundle().apply {
                putString(COIN_SYMBOL, coinSymbol)
            }

            return CoinDetailFragment().apply { arguments = args }
        }
    }
}
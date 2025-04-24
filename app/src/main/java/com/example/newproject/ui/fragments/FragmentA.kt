package com.example.newproject.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newproject.DrinkAdapter
import com.example.newproject.R
import com.example.newproject.databinding.FragmentABinding
import com.example.newproject.datamodels.Drink
import com.example.newproject.utils.PrefsHelper
import com.example.newproject.viewmodel.DrinksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentA : Fragment() {

private lateinit var _binding: FragmentABinding
    private lateinit var adapter: DrinkAdapter
    private val viewModel:DrinksViewModel by activityViewModels()
    private var favList:List<String>? = listOf()
    private val favdrinksList:List<Drink>? = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentABinding.inflate(layoutInflater,container,false)
        return _binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DrinkAdapter(emptyList(), emptyList()) { drink, isFav ->
            Log.e("TAG", "onViewCreated: ${drink.toString()} isAFv ${isFav.toString()}", )
            viewModel.toggleFavorite(drink, isFav)
        }
        viewModel.loadFavorites();
        viewModel.drinksLiveData.observe(viewLifecycleOwner) { drinks ->
            favList = viewModel.favoritesLiveData.value?.map { it.idDrink } ?: emptyList()
            adapter.updateData(drinks, favList!!)

        }

        viewModel.favoritesLiveData.observe(viewLifecycleOwner) { favorites ->
            val favdrinksList = favorites.map { it.idDrink }
            adapter.updateData(viewModel.drinksLiveData.value ?: emptyList(), favdrinksList)
            Log.e("TAG" ,"Updating: ")
        }


        val last = PrefsHelper.getLastSearch(requireContext()) ?: ("name" to "margarita")
        if (last.first == "name") {
            _binding.radioName.isChecked = true
            _binding.searchEditText.setText(last.second)
            viewModel.searchDrinksByName(last.second)
        } else {
            _binding.radioAlphabet.isChecked = true
            _binding.searchEditText.setText(last.second)
            viewModel.searchDrinksByAlphabet(last.second.first())
        }

        _binding.recyclerView.adapter = adapter
        _binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        _binding.searchButton.setOnClickListener {

            var text = _binding.searchEditText.text.toString()
            if (text.isEmpty())
            {
                text = " "
            }
            if (_binding.radioName.isChecked) {
                viewModel.searchDrinksByName(text)
                PrefsHelper.saveLastSearch(requireContext(), "name", text)
            } else {
                if (text.isNotEmpty()) {
                    viewModel.searchDrinksByAlphabet(text.first())
                    PrefsHelper.saveLastSearch(requireContext(), "alphabet", text.first().toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
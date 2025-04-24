package com.example.newproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newproject.DrinkAdapter
import com.example.newproject.R
import com.example.newproject.databinding.FragmentABinding
import com.example.newproject.databinding.FragmentBBinding
import com.example.newproject.datamodels.Drink
import com.example.newproject.viewmodel.DrinksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentB : Fragment() {
    private var _binding:FragmentBBinding?=null
    private val binding get() = _binding
    private val viewModel: DrinksViewModel by activityViewModels()
    private lateinit var adapter: DrinkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DrinkAdapter(emptyList(), emptyList()) { drink, isFav ->
            viewModel.toggleFavorite(drink, isFav)
        }

        binding?.recyclerViewFavorites?.adapter = adapter
        binding?.recyclerViewFavorites?.layoutManager = LinearLayoutManager(requireContext())


        viewModel.drinksLiveData.observe(viewLifecycleOwner) { drinks ->
            val favIds = viewModel.favoritesLiveData.value?.map { it.idDrink } ?: emptyList()
            val favDrinks = drinks.filter { it.idDrink in favIds }
            adapter.updateData(favDrinks, favIds)
        }

//        viewModel.favoritesLiveData.observe(viewLifecycleOwner) {
//            // Re-trigger drinks observer manually (if needed)
//            viewModel.drinksLiveData.value?.let { drinks ->
//                val favIds = it.map { it.idDrink }
//                val favDrinks = drinks.filter { drink -> drink.idDrink in favIds }
//                adapter.updateData(favDrinks, favIds)
//            }
//        }
        // Observe favorites
//        viewModel.favoritesLiveData.observe(viewLifecycleOwner) { favorites ->
//            Log.e("TAG", "onViewCreated: ${favorites.toString()}", )
//            val favdrinksList = favorites.map { it.idDrink }
//            adapter.updateData(viewModel.drinksLiveData.value ?: emptyList(), favdrinksList)
//            Log.e("TAG" ,"Updating: ")
//        }

        // Load favorites
        viewModel.loadFavorites()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
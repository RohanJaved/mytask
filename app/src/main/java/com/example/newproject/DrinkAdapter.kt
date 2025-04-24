package com.example.newproject

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newproject.databinding.ItemDrinkBinding
import com.example.newproject.datamodels.Drink

class DrinkAdapter(
    private var drinks: List<Drink>,
    private var favorites: List<String>,
    private val onFavoriteClick: (Drink, Boolean) -> Unit
) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    inner class DrinkViewHolder(val binding: ItemDrinkBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val binding = ItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinks[position]
        with(holder.binding) {
            tvDrinkName.text = drink.strDrink
            cbAlcoholic.isChecked = drink.strAlcoholic == "Alcoholic"
            Glide.with(root.context).load(drink.strDrinkThumb).circleCrop().into(ivThumbnail)

            val isFav = favorites.contains(drink.idDrink)
            ivFavorite.setImageResource(if (isFav) R.drawable.selected_star else R.drawable.unselected_star)

            ivFavorite.setOnClickListener {
                onFavoriteClick(drink, !isFav)
            }
        }
    }

    override fun getItemCount() = drinks.size

    fun updateData(newDrinks: List<Drink>, newFavs: List<String>) {
        drinks = newDrinks
        favorites = newFavs
        Log.e("Called", "${drinks.toString()}: ", )
        notifyDataSetChanged()
    }
}
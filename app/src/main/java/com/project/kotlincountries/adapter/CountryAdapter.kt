package com.project.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.project.kotlincountries.databinding.ItemCountryBinding
import com.project.kotlincountries.model.CountryModel
import com.project.kotlincountries.view.FeedFragmentDirections

class CountryAdapter (val countryList : ArrayList<CountryModel>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(){
    class CountryViewHolder(val bind : ItemCountryBinding) : RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val bind = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind.apply {
            name.text = countryList[position].countryName
            region.text =  countryList[position].countryRegion
            linearView.setOnClickListener {
                val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
                Navigation.findNavController(it).navigate(action)
            }
        }
    }
    fun updateCoutryList(newCountryList : List<CountryModel>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return countryList.size
    }
}
package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_main.databinding.ItemSearchCityBinding
import com.example.lib_base.model.CityDataModel

/**
 * @CreateDate: 2024/10/12 03:50
 * @Author: 青柠
 * @Description:
 */
class SearchCityAdapter :
    BaseQuickAdapter<CityDataModel, SearchCityAdapter.VH>() {
    class VH(
        parent: ViewGroup,
        val binding: ItemSearchCityBinding = ItemSearchCityBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: CityDataModel?) {
        holder.binding.run {
            if (item == null) return
            data = item
            executePendingBindings()

        }
    }
}
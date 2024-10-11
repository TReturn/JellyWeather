package com.example.lib_main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_base.room.entity.CityEntity
import com.example.lib_main.databinding.ItemCityManagerBinding

/**
 * @CreateDate: 2024/10/12 02:02
 * @Author: 青柠
 * @Description:
 */
class CityManagerAdapter :
    BaseQuickAdapter<CityEntity, CityManagerAdapter.VH>() {
    class VH(
        parent: ViewGroup,
        val binding: ItemCityManagerBinding = ItemCityManagerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int, item: CityEntity?) {
        holder.binding.run {
            if (item == null) return
            data = item
            executePendingBindings()

        }
    }
}
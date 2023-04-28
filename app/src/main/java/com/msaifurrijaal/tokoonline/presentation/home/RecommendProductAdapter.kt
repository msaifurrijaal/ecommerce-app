package com.msaifurrijaal.tokoonline.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msaifurrijaal.tokoonline.databinding.ItemProductBinding

class RecommendProductAdapter: RecyclerView.Adapter<RecommendProductAdapter.ViewHoleder>() {

    private var listener: ((String) -> Unit)? = null

    class ViewHoleder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(listener: ((String) -> Unit)?) {
            itemView.setOnClickListener {
                listener?.let {
                    it("")
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoleder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHoleder(binding)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ViewHoleder, position: Int) {
        holder.bindItem(listener)
    }

    fun onClick(listener: ((String) -> Unit)) {
        this.listener = listener
    }
}
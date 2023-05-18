package com.msaifurrijaal.tokoonline.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msaifurrijaal.tokoonline.BuildConfig
import com.msaifurrijaal.tokoonline.data.model.product.DataProduct
import com.msaifurrijaal.tokoonline.databinding.ItemProductBinding

class RecommendProductAdapter: RecyclerView.Adapter<RecommendProductAdapter.ViewHoleder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataProduct>(){
        override fun areItemsTheSame(oldItem: DataProduct, newItem: DataProduct): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DataProduct, newItem: DataProduct): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallback)

    private var listener: ((DataProduct) -> Unit)? = null

    class ViewHoleder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(listener: ((DataProduct) -> Unit)?, dataProduct: DataProduct) {
            itemView.setOnClickListener {
                listener?.let {
                    it(dataProduct)
                }
            }
            val images = dataProduct.imageProducts
            if (images != null && images.isNotEmpty()){
                val image = images[0]

                if (image != null){
                    val thumbnail = BuildConfig.BASE_URL_IMAGE + image
                    Glide.with(itemView)
                        .load(thumbnail)
                        .placeholder(android.R.color.darker_gray)
                        .into(binding.ivThumbnailProduct)
                }
            }

            binding.tvTitleProduct.text = dataProduct.title
            binding.tvPriceProduct.text = dataProduct.price.toString()
            binding.tvLocationProduct.text = dataProduct.address
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoleder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHoleder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHoleder, position: Int) {
        holder.bindItem(listener, differ.currentList[position])
    }

    fun onClick(listener: ((DataProduct) -> Unit)) {
        this.listener = listener
    }
}
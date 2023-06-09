package com.msaifurrijaal.tokoonline.presentation.resultproduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msaifurrijaal.tokoonline.BuildConfig
import com.msaifurrijaal.tokoonline.data.model.product.DataProduct
import com.msaifurrijaal.tokoonline.databinding.ItemProductBinding

class ResultProductAdapter: RecyclerView.Adapter<ResultProductAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DataProduct>(){
        override fun areItemsTheSame(oldItem: DataProduct, newItem: DataProduct): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DataProduct, newItem: DataProduct): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    private var listener: ((DataProduct) -> Unit)? = null

    class ViewHolder(private val binding: ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dataProduct: DataProduct, listener: ((DataProduct) -> Unit)?) {
            binding.tvTitleProduct.text = dataProduct.title
            binding.tvPriceProduct.text = dataProduct.price.toString()
            binding.tvLocationProduct.text = dataProduct.address

            val images = dataProduct.imageProducts
            if (images != null && images.isNotEmpty()){
                val image = images[0]?.image

                if (image != null){
                    val thumbnail = BuildConfig.BASE_URL_IMAGE + image
                    Glide.with(itemView)
                        .load(thumbnail)
                        .placeholder(android.R.color.darker_gray)
                        .into(binding.ivThumbnailProduct)
                }
            }

            itemView.setOnClickListener {
                listener?.let {
                    it(dataProduct)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(differ.currentList[position], listener)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun onClick(listener: ((DataProduct) -> Unit)){
        this.listener = listener
    }
}
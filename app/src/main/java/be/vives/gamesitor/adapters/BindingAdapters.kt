package be.vives.gamesitor.adapters

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import be.vives.gamesitor.models.Item
import com.bumptech.glide.Glide


@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<Item?>?) {
    val adapter = this.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrlDataGrid")
fun ImageView.bindImage(imgUrl: String?) {
    if (imgUrl != null) {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .centerInside()
            .into(this)
    }

}

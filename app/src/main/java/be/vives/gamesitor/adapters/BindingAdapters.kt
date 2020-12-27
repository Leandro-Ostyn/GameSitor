package be.vives.gamesitor.adapters

import PhotoGridAdapter
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import be.vives.gamesitor.models.Item
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Binding adapter used to hide the spinner once data is available
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url)
        .centerCrop()
        .into(imageView)
}

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

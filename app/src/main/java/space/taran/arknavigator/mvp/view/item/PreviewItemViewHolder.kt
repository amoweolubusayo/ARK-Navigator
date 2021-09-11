package space.taran.arknavigator.mvp.view.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ortiz.touchview.OnTouchImageViewListener
import kotlinx.android.extensions.LayoutContainer
import space.taran.arknavigator.databinding.ItemImageBinding
import space.taran.arknavigator.ui.fragments.utils.PredefinedIcon
import space.taran.arknavigator.mvp.presenter.adapter.PreviewsList
import space.taran.arknavigator.utils.imageForPredefinedIcon
import space.taran.arknavigator.utils.loadZoomImage
import java.nio.file.Path

//todo join with FileItemViewHolder, it is basically the same, just different sizes
class PreviewItemViewHolder(val binding: ItemImageBinding, val presenter: PreviewsList) :
    RecyclerView.ViewHolder(binding.root),
    PreviewItemView {

    override var pos = -1

    override fun setPredefined(resource: PredefinedIcon): Unit = with(binding.root) {
        binding.ivImage.setImageResource(imageForPredefinedIcon(resource))
    }

    override fun setImage(file: Path): Unit = with(binding.root) {
        loadZoomImage(file, binding.ivImage)
    }

    override fun setZoomEnabled(enabled: Boolean): Unit =
        binding.ivImage.let { touchImageView ->
            touchImageView.isZoomEnabled = enabled
            if (enabled) {
                touchImageView.setOnTouchImageViewListener(object : OnTouchImageViewListener {
                    override fun onMove() {
                        presenter.onImageZoom(touchImageView.isZoomed)
                    }
                })
            } else {
                touchImageView.setOnTouchImageViewListener(object : OnTouchImageViewListener {
                    override fun onMove() {}
                })
            }
        }

    override fun resetZoom() = binding.ivImage.resetZoom()
}
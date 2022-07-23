import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.NewsActivity
import com.example.app1.R
import com.example.app1.model.NewsData
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import org.jsoup.Jsoup
import java.net.URL
import android.graphics.Bitmap
import android.util.Log
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Float.min
import kotlin.system.measureTimeMillis


class CustomAdapter(private val mList: List<NewsData>, private val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    // costruisce gli elementi della recyclerview sulla base della view rv_row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_row, parent, false)
        return ViewHolder(view)
    }
    // popola la row con i dati delle notizia
    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        val text = MutableLiveData(item.title)

        holder.cv_news.setOnTouchListener(
            View.OnTouchListener { view, event ->
                // variables to store current configuration of quote card.
                val displayMetrics = context.resources.displayMetrics
                val cardWidth = holder.cv_news.width
                val cardStart = (displayMetrics.widthPixels.toFloat() / 2) - (cardWidth / 2)
                val newX = event.rawX
                val minDistance = context.resources.getInteger(R.integer.minDistance)

                when (event.action) {
                    //inizio gesture
                    MotionEvent.ACTION_MOVE -> {
                        // TODO: Handle ACTION_MOVE
                        if (newX - cardWidth < cardStart) { // or newX < cardStart + cardWidth
                            holder.cv_news.animate().x(minOf(cardStart, newX - (cardWidth / 2)))
                                .setDuration(0)
                                .start()
                        }
                    }
                    //release
                    MotionEvent.ACTION_UP -> {
                        var currentX = holder.cv_news.x
                        holder.cv_news.animate()
                            .x(cardStart)
                            .setDuration(50)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    if (currentX < -5f) {
                                            //cambiamento dei dati visualizzati
                                            if (holder.tv_title.currentTextColor == ContextCompat.getColor(
                                                    context,
                                                    R.color.main
                                                )
                                            ) {
                                                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                    Html.fromHtml(
                                                        item.desc,
                                                        Html.FROM_HTML_MODE_COMPACT
                                                    )
                                                } else {
                                                    @Suppress("DEPRECATION")
                                                    Html.fromHtml(item.desc)
                                                }).also {
                                                    text.value = it.toString()
                                                }
                                                holder.tv_title.setTextColor(ContextCompat.getColor( context, R.color.dark_grey))
                                            } else {
                                                (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                    Html.fromHtml(
                                                        item.title,
                                                        Html.FROM_HTML_MODE_COMPACT
                                                    )
                                                } else {
                                                    @Suppress("DEPRECATION")
                                                    Html.fromHtml(item.title)
                                                }).also { text.value = it.toString() }
                                                holder.tv_title.setTextColor(ContextCompat.getColor( context, R.color.main))
                                            }
                                        } else {
                                            view.performClick()
                                        }
                                    currentX = 20f
                                    holder.tv_title.text = text.value
                                }
                            })
                            .start()
                    }
                }

                // required to by-pass lint warning
                return@OnTouchListener true
            }
        )

        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(item.title, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(item.title)
        }).also { text.value = it.toString() }

        holder.tv_title.text = text.value

        setCategory(holder, item.category)
        item.icon.let { holder.iv_favicon.setImageBitmap(it) }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsActivity::class.java).apply {
                putExtra("TITLE", item.title)
                putExtra("HTML_CONTENT", item.link)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCategory(holder: ViewHolder, category: Int){
        val categories = context.resources.getStringArray(R.array.topics_it)
        holder.tv_category.text = categories[category] ?: ""
        val radius: Float = context.resources.getDimension(R.dimen.default_corner_radius)
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius.toFloat())
            .build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        val colorStateList = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(context.resources.getIntArray(R.array.topics_colors)[category])
        )
        shapeDrawable.fillColor = colorStateList
        ViewCompat.setBackground(holder.tv_category, shapeDrawable)
        holder.tv_category.setTextColor(ContextCompat.getColor(context, R.color.white))
    }


    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cv_news : CardView = itemView.findViewById(R.id.cv_news)
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val iv_favicon: ImageView = itemView.findViewById(R.id.iv_favicon)
        val tv_category: TextView = itemView.findViewById(R.id.tv_category)
    }
}




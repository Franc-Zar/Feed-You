import android.R.attr.radius
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
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
import org.jsoup.Jsoup
import java.net.URL


class CustomAdapter(private val mList: List<NewsData>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    var context: Context? = null

    // costruisce gli elementi della recyclerview sulla base della view rv_row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_row, parent, false)
        context = parent.context
        return ViewHolder(view)
    }
    // popola la row con i dati delle notizia
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        val categories = context?.resources?.getStringArray(R.array.topics_it)
        (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(item.title, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(item.title)
        }).also { holder.tv_title.text = it }


        holder.tv_category.text = categories?.get(item.category) ?: ""
        holder.tv_category.setBackgroundColor(
            context?.resources?.getIntArray(R.array.topics_colors)?.get(item.category) ?: R.color.black)
        context?.let { ContextCompat.getColor(it, R.color.white)}
        var icon : org.jsoup.nodes.Element? = null
        val thread = Thread {
            try {
                //TODO ristudiare il reindirizzamento, soluzione temporanea è google.com
                val doc = URL(item.guid?.value ?: "http://www.google.com/").readText()

                icon = try{
                    Jsoup.parse(doc).head().select("link[href~=.*\\.(ico|png)]").first()
                }catch (e: Exception) {
                    try {
                        Jsoup.parse(doc).head().select("meta[itemprop=image]").first()
                    } catch (e: Exception) {
                        null
                    }
                }
                var favicon = icon?.attributes()?.get("href")
                if (favicon?.startsWith('/') == true){
                    favicon = favicon.substringAfter('/')
                    if (favicon.startsWith('/')){
                        favicon = favicon.substringAfter('/')
                    }
                    if (! favicon.startsWith("www")){
                        val baseurl = item.link.split('/')
                        favicon  = baseurl[0] + "//" + baseurl[2] + '/' + favicon
                    }else{
                        favicon = "https://" + favicon
                    }
                }

                val image = BitmapFactory.decodeStream(URL(favicon).openConnection().getInputStream())
                holder.iv_favicon.setImageBitmap(image)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsActivity::class.java).apply {
                putExtra("TITLE", item.title)
                putExtra("HTML_CONTENT", item.link)
            }
            holder.itemView.context.startActivity(intent)
            //val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            //holder.itemView.context.startActivity(browserIntent)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val iv_favicon: ImageView = itemView.findViewById(R.id.iv_favicon)
        val tv_category: TextView = itemView.findViewById(R.id.tv_category)
    }
}




import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.NewsActivity
import com.example.app1.NewsData
import com.example.app1.R
import java.net.URL


class CustomAdapter(private val mList: List<NewsData>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // costruisce gli elementi della recyclerview sulla base della view rv_row
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Fa inflate di rv_row view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_row, parent, false)
        return ViewHolder(view)
    }

    // popola la row con i dati delle notizia
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.tv_title.text = item.title
        holder.tv_desc.text = item.desc
        var html = ""
        val thread = Thread {
            try {
                html = URL(item.guid?.value ?: "http://www.google.com/").readText()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsActivity::class.java).apply {
                //putExtra("HTML_CONTENT", html)
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
        val tv_desc: TextView = itemView.findViewById(R.id.tv_desc)
    }
}




package net.alhazmy13.demoproject

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var adapter: SimpleArrayAdapter? = null
    private val dataSet: MutableList<PostModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        adapter = SimpleArrayAdapter(dataSet)
        listView!!.adapter = adapter

        DownloadTask().execute("https://jsonplaceholder.typicode.com/posts")

    }

    private inner class DownloadTask : AsyncTask<String, Void, List<PostModel>>() {

        override fun doInBackground(vararg params: String): List<PostModel> {
            Log.d("TAG",params[0])
            val connection = URL(params[0]).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))

            val response = Gson().fromJson(bufferedReader, Array<PostModel>::class.java).asList()
            return response


        }

        override fun onPostExecute(response: List<PostModel>) {
            //Here you are done with the task

            dataSet.clear()
            dataSet.addAll(response)
            adapter!!.notifyDataSetChanged()

        }
    }


    inner class SimpleArrayAdapter(private val values: MutableList<PostModel>) : ArrayAdapter<PostModel>(this@MainActivity, -1, values) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView = inflater.inflate(R.layout.item_post, parent, false)
            val body: TextView = rowView.findViewById(R.id.txt_body)
            val title: TextView = rowView.findViewById(R.id.txt_title)
            body.text = values.get(position).getBody()
            title.text = values.get(position).getTitle()

            return rowView
        }
    }
}

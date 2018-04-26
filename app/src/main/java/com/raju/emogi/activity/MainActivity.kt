package com.raju.emogi.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.raju.emogi.R
import com.raju.emogi.adapter.EmogiAdapter
import com.raju.emogi.adapter.GridSpaceItemDecoration
import com.raju.emogi.data.model.Assets
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by Raju Yadav
 */

class MainActivity : AppCompatActivity(), MainView {

    private var mainActivityPresenter: MainActivityPresenter? = null
    private var emogiAdapter: EmogiAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityPresenter = MainActivityPresenter(this)
    }

    override fun initializeView() {
        //Setting grid view for tablet with count 3 and for mobile device count 2
        val columnCount = resources.getInteger(R.integer.list_column_count)
        if (columnCount <= 1) list.layoutManager = GridLayoutManager(this, 3) else list.layoutManager = GridLayoutManager(this, 4)

        //Setting RecyclerView ItemDecoration
        list.addItemDecoration(GridSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.card_spacing)))
    }

    private fun handleSearchQuery() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!TextUtils.isEmpty(query)) {
                    mainActivityPresenter?.getFilteredData(query)
                    return true // handled
                }
                // Let the SearchView perform the default action.
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!TextUtils.isEmpty(newText)) {
                    mainActivityPresenter?.getFilteredData(newText)
                    return true // handled
                }
                // The SearchView should perform the default action of showing any suggestions if
                // available.
                return false
            }
        })
    }

    override fun enableSearchView(enable : Boolean) {
        if(enable){
            enableSearchView(search, enable)
            handleSearchQuery();
        }

    }

    private fun enableSearchView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                enableSearchView(child, enabled)
            }
        }
    }

    override fun showThumbSizeAssetsList(assets: ArrayList<Assets>?) {
        //Setting RecyclerView adapter
        emogiAdapter = EmogiAdapter(assets)
        list.adapter = emogiAdapter
        emogiAdapter?.notifyDataSetChanged()
    }

    override fun showFullSizeAssetsList(assets: ArrayList<Assets>?) {
        // Not implemented if we want to show full size image
        // then we can send through this method
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter?.Destroy()

    }
}

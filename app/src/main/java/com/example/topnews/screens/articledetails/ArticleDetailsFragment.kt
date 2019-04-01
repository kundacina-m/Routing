package com.example.topnews.screens.articledetails


import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.topnews.R
import com.example.topnews.widget.ReadLaterWidget
import com.example.topnews.screens.Article
import com.example.topnews.utils.Constants.MAP_SOURCE_KEY_NAME
import com.example.topnews.utils.Constants.PARCEL_FOR_ARTICLE_DETAILS
import com.example.topnews.db.ArticleDaoImpl
import com.example.topnews.utils.Constants.TRANSITION_ENABLED
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_frame.*
import kotlinx.android.synthetic.main.fragment_article_details.*
import android.content.Intent
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class ArticleDetailsFragment : Fragment() {

    private lateinit var menu: Menu

    private val articleDAO by lazy {
        ArticleDaoImpl()
    }

    private val dataItem by lazy {
        arguments?.getParcelable(PARCEL_FOR_ARTICLE_DETAILS) as Article
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.bottom_navigation?.visibility = View.GONE
        actionBarSetup()
        setTransitionElements()


        if (arguments?.getBoolean(TRANSITION_ENABLED)!!) setOnTransitionEnterEndListener()
        else fillViewWithData()

        setCollapsedImgListener()

        btReadLater.setOnClickListener {
            addArticleToFavourites(dataItem)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.details_menu, menu)
        this.menu = menu
        setOnMenuItemClickListener()
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        activity?.bottom_navigation?.visibility = View.VISIBLE
        super.onDestroyView()
    }


    private fun actionBarSetup() {
        (activity as AppCompatActivity).setSupportActionBar(article_app_bar)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun setTransitionElements() {
        ViewCompat.setTransitionName(ivArticleImage, dataItem.publishedAt)
        ViewCompat.setTransitionName(tvTitle, dataItem.publishedAt + "title")
        tvTitle.text = dataItem.title

        val options = RequestOptions()
            .centerCrop()
            .error(R.drawable.error_img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(activity!!).load(dataItem.urlToImage).apply(options)
            .into(ivArticleImage)
    }

    private fun setCollapsedImgListener() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                menu.findItem(R.id.addToFavourites).isVisible = true
                menu.findItem(R.id.shareArticle).isVisible = true
                btReadLater.visibility = View.GONE
            } else {
                menu.findItem(R.id.addToFavourites).isVisible = false
                menu.findItem(R.id.shareArticle).isVisible = false
                btReadLater.visibility = View.VISIBLE
            }
        })
    }

    private fun setOnMenuItemClickListener() {
        menu.findItem(R.id.addToFavourites).setOnMenuItemClickListener {
            addArticleToFavourites(dataItem)
            true
        }
        menu.findItem(R.id.shareArticle).setOnMenuItemClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, dataItem.url)
            }
            startActivity(Intent.createChooser(shareIntent, "choose one"))
            true
        }
    }

    private fun fillViewWithData() {
        tvSource.text = dataItem.source.get(key = MAP_SOURCE_KEY_NAME)
        tvDescription.text = dataItem.description
        tvContent.text = dataItem.content
        tvPublishedAt.text = dataItem.publishedAt
        tvAuthor.text = dataItem.author
        linkToWeb.text = dataItem.url
    }

    private fun addArticleToFavourites(article: Article) {
        articleDAO.insertItem(article).executeAsync {
            if (it)
                Toast.makeText(activity, "Article successfully added to favourites!", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(activity, "Article successfully removed from favourites!", Toast.LENGTH_LONG).show()
            ReadLaterWidget.WidgetRefresher.sendRefreshBroadcast(context!!)
        }

    }

    private fun setOnTransitionEnterEndListener() {
        setEnterSharedElementCallback(object : SharedElementCallback() {

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                fillViewWithData()
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
            }
        })
    }

}

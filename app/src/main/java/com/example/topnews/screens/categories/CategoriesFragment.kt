package com.example.topnews.screens.categories

import com.example.topnews.R
import com.example.topnews.screens.ArticleViewModel
import base.BaseFragment

class CategoriesFragment : BaseFragment<ArticleViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_categories
    override fun getClassTypeVM(): Class<ArticleViewModel> = ArticleViewModel::class.java

    override fun initView() {}

}

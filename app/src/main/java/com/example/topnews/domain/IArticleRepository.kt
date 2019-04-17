package com.example.topnews.domain

import com.example.topnews.data.model.Article
import com.example.topnews.domain.crud.LocalCRUD
import com.example.topnews.domain.crud.RemoteCRUD

interface IArticleRepository : LocalCRUD<Article>,
							   RemoteCRUD<Article>

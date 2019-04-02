package com.example.topnews.repository

interface IArticleRepository<T> : LocalCRUD<T>, RemoteCRUD<T>
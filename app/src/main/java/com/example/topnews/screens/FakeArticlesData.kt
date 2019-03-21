package com.example.topnews.screens

import com.example.topnews.screens.categories.RV
import com.example.topnews.screens.categories.Section
import com.example.topnews.screens.categories.SectionItem

object FakeData {

    fun fetchData(): MutableList<Article> {
        return data
    }

    fun fetchSearchedData(num: Int): MutableList<Article> {
        return data.subList(0, num)
    }

    fun fetchRV(): MutableList<RV> {
        return list
    }

    private val list = mutableListOf<RV>(
        Section("Sport"),
        SectionItem("Kosarka", 1),
        SectionItem("Odbojka", 2),
        SectionItem("Rukomet", 3),
        SectionItem("Vaterpolo", 4),
        Section("Novine"),
        SectionItem("BBC", 5),
        SectionItem("CNN", 6),
        SectionItem("RT", 7)
    )

    private val data = mutableListOf<Article>(
        Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        ), Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        ), Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        ), Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        ), Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        ), Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        ), Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        ), Article(
            hashMapOf<String, String>(
                "id" to "bbc-news",
                "name" to "BBC News"
            ),
            "BBC News",
            "Ex-Nissan boss Ghosn released on bail",
            "Carlos Ghosn is released on bail set at $9m after more than 100 days in detention in Japan.",
            "http://www.bbc.co.uk/news/business-47466766",
            "https://ichef.bbci.co.uk/news/1024/branded_news/7A23/production/_97176213_breaking_news_bigger.png",
            "2019-03-06T07:44:23Z",
            "Former Nissan boss Carlos Ghosn has left prison in Japan on bail more than three months after being arrested." +
                    "\r\nA Tokyo court made the surprise decision to allow his release on Tuesday, setting bail at 1bn yen " +
                    "(£6.8m; $8.9m).\r\nMr Ghosn has been charged with fi… [+461 chars]"
        )
    )
}
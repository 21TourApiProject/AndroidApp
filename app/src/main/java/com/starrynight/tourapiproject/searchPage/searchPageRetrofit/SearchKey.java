package com.starrynight.tourapiproject.searchPage.searchPageRetrofit;

public class SearchKey {
    Filter filter;
    String keyword;

    public SearchKey(Filter filter, String keyword) {
        this.filter = filter;
        this.keyword = keyword;
    }
}

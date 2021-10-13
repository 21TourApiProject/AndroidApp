package com.starrynight.tourapiproject.touristPointPage.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchData {
    @SerializedName("documents")
    public List<Document> Searchdocuments;
    @SerializedName("meta")
    public Meta meta;

    public static class Meta {
        @SerializedName("total_count")
        public int total_count;
        @SerializedName("pageable_count")
        public int pageable_count;
        @SerializedName("is_end")
        public boolean is_end;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getPageable_count() {
            return pageable_count;
        }

        public void setPageable_count(int pageable_count) {
            this.pageable_count = pageable_count;
        }

        public boolean isIs_end() {
            return is_end;
        }

        public void setIs_end(boolean is_end) {
            this.is_end = is_end;
        }
    }

    public static class Document {
        String title;
        String contents;
        String url;
        String blogname;
        String thumbnail;
        String datetime;

        public String getTitle() {
            title = title.replaceAll("<b>", "");
            title = title.replaceAll("</b>", "");
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContents() {
            contents = contents.replaceAll("<b>", "");
            contents = contents.replaceAll("</b>", "");
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBlogname() {
            blogname = blogname.replaceAll("<b>", "");
            blogname = blogname.replaceAll("</b>", "");
            return blogname;
        }

        public void setBlogname(String blogname) {
            this.blogname = blogname;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }
}

package com.starrynight.tourapiproject.TouristspotPage;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class SearchData {
public  Meta meta;
 public class Meta{
     @SerializedName("total_count")
     public int total_count;
     @SerializedName("pageable_count")
     public int pageable_count;
     @SerializedName("is_end")
     public boolean is_end;
     @SerializedName("body")
     public List<SearchDocument>body;

     public List<SearchDocument> getBody() {
         return body;
     }

     public void setBody(List<SearchDocument> body) {
         this.body = body;
     }

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
}

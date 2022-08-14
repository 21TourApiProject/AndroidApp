package com.starrynight.tourapiproject.postItemPage;
/**
* @className : Search_item
* @description : 게시물 작성 시 관측지 찾는 페이지에서 필요한 관측지 검색 아이템 입니다.
* @modification : jinhyeok (2022-08-14) 주석 수정
* @author : 2022-08-14
* @date : jinhyeok
* @version : 1.0
   ====개정이력(Modification Information)====
  수정일        수정자        수정내용
   -----------------------------------------
   jinhyeok      2022-08-14       주석 수정

 */
public class Search_item {
    String itemName;
    String address;

    public Search_item(String itemName, String address) {
        this.itemName = itemName;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}

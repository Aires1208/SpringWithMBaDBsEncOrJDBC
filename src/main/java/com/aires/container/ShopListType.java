package com.aires.container;

/**
 * Created by 10183966 on 2017/2/17.
 */
public enum ShopListType {

    BLACK_LIST(0, "黑名单"),
    WHITE_LIST(1, "白名单"),
    INVITE_LIST(2, "邀请名单"),
    RECOMMEND_WHITE_LIST(3, "推荐白名单"),
    RECOMMEND_BLACK_LIST(4, "推荐黑名单");

    private int type;

    private String description;

    ShopListType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getValue() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}

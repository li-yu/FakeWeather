package com.liyu.fakeweather.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuyu on 2016/12/8.
 */

public class JiandanXXOO implements Serializable {

    /**
     * comment_ID : 3342601
     * comment_post_ID : 21183
     * comment_author : 小宝
     * comment_author_email : ooxx@live.com
     * comment_author_url :
     * comment_author_IP : 58.49.10.26
     * comment_date : 2016-12-08 13:54:48
     * comment_date_gmt : 2016-12-08 05:54:48
     * comment_content : <img src="http://ww3.sinaimg.cn/mw600/66b3de17gw1fabanioq3hj20dw0iitaa.jpg" />
     * comment_karma : 0
     * comment_approved : 1
     * comment_agent : Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36 OPR/41.0.2353.69
     * comment_type :
     * comment_parent : 0
     * user_id : 0
     * comment_subscribe : N
     * comment_reply_ID : 0
     * vote_positive : 67
     * vote_negative : 6
     * vote_ip_pool :
     * text_content :
     * pics : ["http://ww3.sinaimg.cn/mw600/66b3de17gw1fabanioq3hj20dw0iitaa.jpg"]
     * videos : []
     */

    private String comment_ID;
    private String comment_post_ID;
    private String comment_author;
    private String comment_author_email;
    private String comment_author_url;
    private String comment_author_IP;
    private String comment_date;
    private String comment_date_gmt;
    private String comment_content;
    private String comment_karma;
    private String comment_approved;
    private String comment_agent;
    private String comment_type;
    private String comment_parent;
    private String user_id;
    private String comment_subscribe;
    private String comment_reply_ID;
    private String vote_positive;
    private String vote_negative;
    private String vote_ip_pool;
    private String text_content;
    private List<String> pics;
    private List<?> videos;
    private int girlHeight;
    private int girlWidth;

    public int getGirlHeight() {
        return girlHeight;
    }

    public void setGirlHeight(int girlHeight) {
        this.girlHeight = girlHeight;
    }

    public int getGirlWidth() {
        return girlWidth;
    }

    public void setGirlWidth(int girlWidth) {
        this.girlWidth = girlWidth;
    }

    public String getComment_ID() {
        return comment_ID;
    }

    public void setComment_ID(String comment_ID) {
        this.comment_ID = comment_ID;
    }

    public String getComment_post_ID() {
        return comment_post_ID;
    }

    public void setComment_post_ID(String comment_post_ID) {
        this.comment_post_ID = comment_post_ID;
    }

    public String getComment_author() {
        return comment_author;
    }

    public void setComment_author(String comment_author) {
        this.comment_author = comment_author;
    }

    public String getComment_author_email() {
        return comment_author_email;
    }

    public void setComment_author_email(String comment_author_email) {
        this.comment_author_email = comment_author_email;
    }

    public String getComment_author_url() {
        return comment_author_url;
    }

    public void setComment_author_url(String comment_author_url) {
        this.comment_author_url = comment_author_url;
    }

    public String getComment_author_IP() {
        return comment_author_IP;
    }

    public void setComment_author_IP(String comment_author_IP) {
        this.comment_author_IP = comment_author_IP;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_date_gmt() {
        return comment_date_gmt;
    }

    public void setComment_date_gmt(String comment_date_gmt) {
        this.comment_date_gmt = comment_date_gmt;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_karma() {
        return comment_karma;
    }

    public void setComment_karma(String comment_karma) {
        this.comment_karma = comment_karma;
    }

    public String getComment_approved() {
        return comment_approved;
    }

    public void setComment_approved(String comment_approved) {
        this.comment_approved = comment_approved;
    }

    public String getComment_agent() {
        return comment_agent;
    }

    public void setComment_agent(String comment_agent) {
        this.comment_agent = comment_agent;
    }

    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public String getComment_parent() {
        return comment_parent;
    }

    public void setComment_parent(String comment_parent) {
        this.comment_parent = comment_parent;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment_subscribe() {
        return comment_subscribe;
    }

    public void setComment_subscribe(String comment_subscribe) {
        this.comment_subscribe = comment_subscribe;
    }

    public String getComment_reply_ID() {
        return comment_reply_ID;
    }

    public void setComment_reply_ID(String comment_reply_ID) {
        this.comment_reply_ID = comment_reply_ID;
    }

    public String getVote_positive() {
        return vote_positive;
    }

    public void setVote_positive(String vote_positive) {
        this.vote_positive = vote_positive;
    }

    public String getVote_negative() {
        return vote_negative;
    }

    public void setVote_negative(String vote_negative) {
        this.vote_negative = vote_negative;
    }

    public String getVote_ip_pool() {
        return vote_ip_pool;
    }

    public void setVote_ip_pool(String vote_ip_pool) {
        this.vote_ip_pool = vote_ip_pool;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<?> getVideos() {
        return videos;
    }

    public void setVideos(List<?> videos) {
        this.videos = videos;
    }
}

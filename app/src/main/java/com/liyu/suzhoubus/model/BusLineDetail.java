package com.liyu.suzhoubus.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyu on 2016/10/31.
 */

public class BusLineDetail extends DataSupport implements Serializable {

    /**
     * LGUID : 2c5609cc-f7b2-4a88-a157-75e0d2bb5451
     * LName : 178路
     * LDirection : 独墅湖高教区首末站-火车站
     * LFStdFTime : 05:50
     * LFStdETime : 22:20
     * StandInfo : [{"SName":"独墅湖高教区首末站","SCode":"FGT","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"科教人才市场","SCode":"BTX","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"文荟广场西","SCode":"CJB","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"翰林邻里中心","SCode":"FCS","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"西交大","SCode":"DHB","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"人大国际学院","SCode":"RBG","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"中科大","SCode":"ENT","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"独墅湖图书馆","SCode":"NRD","InTime":"","is_vicinity":0,"s_num":-100,"s_num_str":""},{"SName":"莲花新村五区","SCode":"MMZ","InTime":"14:46:05","is_vicinity":0,"s_num":-1,"s_num_str":"苏E-2W683  已经进站 14:46:05"},{"SName":"莲花新村六区","SCode":"ATK","InTime":"","is_vicinity":0,"s_num":0,"s_num_str":""},{"SName":"莲花新村七区","SCode":"BZR","InTime":"","is_vicinity":0,"s_num":1,"s_num_str":""},{"SName":"塘浦路东","SCode":"DVA","InTime":"","is_vicinity":0,"s_num":2,"s_num_str":""},{"SName":"金鸡湖学校","SCode":"RNN","InTime":"","is_vicinity":0,"s_num":3,"s_num_str":""},{"SName":"邻瑞广场","SCode":"RNK","InTime":"","is_vicinity":0,"s_num":4,"s_num_str":""},{"SName":"邻瑞广场北","SCode":"AWK","InTime":"","is_vicinity":0,"s_num":5,"s_num_str":""},{"SName":"金湖","SCode":"GPJ","InTime":"","is_vicinity":0,"s_num":6,"s_num_str":""},{"SName":"金鸡湖大酒店","SCode":"CBF","InTime":"","is_vicinity":0,"s_num":7,"s_num_str":""},{"SName":"李公堤南","SCode":"MNH","InTime":"14:45:38","is_vicinity":0,"s_num":-1,"s_num_str":"苏E-2W175  已经进站 14:45:38"},{"SName":"高尔夫花园","SCode":"DVY","InTime":"","is_vicinity":0,"s_num":0,"s_num_str":""},{"SName":"城邦花园","SCode":"CWH","InTime":"","is_vicinity":0,"s_num":1,"s_num_str":""},{"SName":"水坊路西","SCode":"NFM","InTime":"","is_vicinity":0,"s_num":2,"s_num_str":""},{"SName":"馨都广场","SCode":"FSD","InTime":"","is_vicinity":0,"s_num":3,"s_num_str":""},{"SName":"嘉怡苑","SCode":"JMS","InTime":"","is_vicinity":0,"s_num":4,"s_num_str":""},{"SName":"国际大厦西","SCode":"JMP","InTime":"","is_vicinity":0,"s_num":5,"s_num_str":""},{"SName":"都市花园南","SCode":"BUV","InTime":"","is_vicinity":0,"s_num":6,"s_num_str":""},{"SName":"中央公园北","SCode":"GZX","InTime":"","is_vicinity":0,"s_num":7,"s_num_str":""},{"SName":"海关大楼","SCode":"FMT","InTime":"","is_vicinity":0,"s_num":8,"s_num_str":""},{"SName":"韶山花园","SCode":"BWT","InTime":"","is_vicinity":0,"s_num":9,"s_num_str":""},{"SName":"东环路","SCode":"GZF","InTime":"14:46:16","is_vicinity":0,"s_num":-1,"s_num_str":"苏E-3L937  已经进站 14:46:16"},{"SName":"苏大北校区","SCode":"DDY","InTime":"","is_vicinity":0,"s_num":0,"s_num_str":""},{"SName":"苏大理想眼科医院","SCode":"RWF","InTime":"","is_vicinity":0,"s_num":1,"s_num_str":""},{"SName":"相门","SCode":"GZU","InTime":"","is_vicinity":0,"s_num":2,"s_num_str":""},{"SName":"醋坊桥观前街东","SCode":"ECV","InTime":"14:44:07","is_vicinity":0,"s_num":-1,"s_num_str":"苏E-2W700  已经进站 14:44:07"},{"SName":"市立医院东区东","SCode":"BXW","InTime":"","is_vicinity":0,"s_num":0,"s_num_str":""},{"SName":"苏州博物馆（拙政园、狮子林）","SCode":"ECN","InTime":"","is_vicinity":0,"s_num":1,"s_num_str":""},{"SName":"齐门","SCode":"FWP","InTime":"","is_vicinity":0,"s_num":2,"s_num_str":""},{"SName":"光华桥西","SCode":"ATF","InTime":"","is_vicinity":0,"s_num":3,"s_num_str":""},{"SName":"火车站","SCode":"RAY","InTime":"14:38:40","is_vicinity":0,"s_num":-1,"s_num_str":"苏E-2W682  已经进站 14:38:40"}]
     * is_favorite : 0
     */

    private long id;
    private String LGUID;
    private String LName;
    private String LDirection;
    private String LFStdFTime;
    private String LFStdETime;
    private int is_favorite;//服务端的标志暂时未使用
    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private List<StandInfoBean> StandInfo = new ArrayList<StandInfoBean>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLGUID() {
        return LGUID;
    }

    public void setLGUID(String LGUID) {
        this.LGUID = LGUID;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getLDirection() {
        return LDirection;
    }

    public void setLDirection(String LDirection) {
        this.LDirection = LDirection;
    }

    public String getLFStdFTime() {
        return LFStdFTime;
    }

    public void setLFStdFTime(String LFStdFTime) {
        this.LFStdFTime = LFStdFTime;
    }

    public String getLFStdETime() {
        return LFStdETime;
    }

    public void setLFStdETime(String LFStdETime) {
        this.LFStdETime = LFStdETime;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public List<StandInfoBean> getStandInfo() {
        return StandInfo;
    }

    public void setStandInfo(List<StandInfoBean> StandInfo) {
        this.StandInfo = StandInfo;
    }

}

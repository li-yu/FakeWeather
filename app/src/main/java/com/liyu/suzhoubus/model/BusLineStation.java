package com.liyu.suzhoubus.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyu on 2016/11/1.
 */

public class BusLineStation implements Serializable {

    /**
     * is_favorite : 0
     * info : {"SCode":"FGT","SName":"独墅湖高教区首末站","baidu_lat":31.273948,"baidu_lng":120.756378,"address":"林泉街场内东","long_info":"约852.4米"}
     * list : [{"Guid":"0a43fd82-6932-4034-ab7e-287f42c6f436","LName":"142路","LDirection":"独墅湖高教区首末站=>官渎里立交桥","SName":"独墅湖高教区首末站","Distince":-1,"Distince_str":"待发车"},{"Guid":"192EA3E1-2058-45E7-A90B-4B87B74EEB95","LName":"812路","LDirection":"独墅湖高教区首末站=>采莲换乘中心（商业街）","SName":"独墅湖高教区首末站","Distince":-1,"Distince_str":"待发车"},{"Guid":"5411cd57-51d2-40e1-9ba9-2dc6a49bfc54","LName":"178路","LDirection":"火车站=>独墅湖高教区首末站","SName":"独墅湖高教区首末站","Distince":9,"Distince_str":"9站"},{"Guid":"2c5609cc-f7b2-4a88-a157-75e0d2bb5451","LName":"178路","LDirection":"独墅湖高教区首末站=>火车站","SName":"独墅湖高教区首末站","Distince":-1,"Distince_str":"待发车"},{"Guid":"a8700272-f551-4751-b07d-7c719086f0ce","LName":"228路","LDirection":"独墅湖高教区首末站=>青剑湖学校","SName":"独墅湖高教区首末站","Distince":-1,"Distince_str":"待发车"},{"Guid":"e128b409-921e-43db-9c99-c78b0f0c8fad","LName":"夜2路","LDirection":"火车站=>独墅湖高教区首末站","SName":"独墅湖高教区首末站","Distince":-1,"Distince_str":"待发车"},{"Guid":"e0e5561a-32ea-432d-ac98-38eed8c4e448","LName":"快线2号路","LDirection":"独墅湖高教区首末站=>火车站","SName":"独墅湖高教区首末站","Distince":-1,"Distince_str":"待发车"}]
     */

    private int is_favorite;
    /**
     * SCode : FGT
     * SName : 独墅湖高教区首末站
     * baidu_lat : 31.273948
     * baidu_lng : 120.756378
     * address : 林泉街场内东
     * long_info : 约852.4米
     */

    private InfoBean info;
    /**
     * Guid : 0a43fd82-6932-4034-ab7e-287f42c6f436
     * LName : 142路
     * LDirection : 独墅湖高教区首末站=>官渎里立交桥
     * SName : 独墅湖高教区首末站
     * Distince : -1
     * Distince_str : 待发车
     */

    private List<ListBean> list;

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        private String SCode;
        private String SName;
        private double baidu_lat;
        private double baidu_lng;
        private String address;
        private String long_info;

        public String getSCode() {
            return SCode;
        }

        public void setSCode(String SCode) {
            this.SCode = SCode;
        }

        public String getSName() {
            return SName;
        }

        public void setSName(String SName) {
            this.SName = SName;
        }

        public double getBaidu_lat() {
            return baidu_lat;
        }

        public void setBaidu_lat(double baidu_lat) {
            this.baidu_lat = baidu_lat;
        }

        public double getBaidu_lng() {
            return baidu_lng;
        }

        public void setBaidu_lng(double baidu_lng) {
            this.baidu_lng = baidu_lng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLong_info() {
            return long_info;
        }

        public void setLong_info(String long_info) {
            this.long_info = long_info;
        }
    }

    public static class ListBean {
        private String Guid;
        private String LName;
        private String LDirection;
        private String SName;
        private int Distince;
        private String Distince_str;

        public String getGuid() {
            return Guid;
        }

        public void setGuid(String Guid) {
            this.Guid = Guid;
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

        public String getSName() {
            return SName;
        }

        public void setSName(String SName) {
            this.SName = SName;
        }

        public int getDistince() {
            return Distince;
        }

        public void setDistince(int Distince) {
            this.Distince = Distince;
        }

        public String getDistince_str() {
            return Distince_str;
        }

        public void setDistince_str(String Distince_str) {
            this.Distince_str = Distince_str;
        }
    }
}

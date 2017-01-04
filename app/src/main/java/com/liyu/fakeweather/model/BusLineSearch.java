package com.liyu.fakeweather.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liyu on 2016/10/31.
 */

public class BusLineSearch implements Serializable {


    /**
     * Guid : 5411cd57-51d2-40e1-9ba9-2dc6a49bfc54
     * LName : 178路
     * LDirection : 火车站->独墅湖高教区首末站
     */

    private List<LineInfo> list;

    public List<LineInfo> getList() {
        return list;
    }

    public void setList(List<LineInfo> list) {
        this.list = list;
    }

    public static class LineInfo {
        private String Guid;
        private String LName;
        private String LDirection;

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
    }
}

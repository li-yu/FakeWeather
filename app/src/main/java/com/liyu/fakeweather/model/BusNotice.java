package com.liyu.fakeweather.model;

/**
 * Created by liyu on 2017/3/17.
 */

public class BusNotice {

    /**
     * status : 200
     * exception :
     * code : 0
     * message :
     * data : {"items":{"id":"1","title":"公告","img_src":"http://weizhang.wisesz.cc/attaches/image/20170317/2869b9218d84bcd05fdad7722cc4baf2.jpg","url":"http://news.wisesz.cc/web/share/regular/320500/323599","red_doit":"1"}}
     */

    private int status;
    private String exception;
    private int code;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * items : {"id":"1","title":"公告","img_src":"http://weizhang.wisesz.cc/attaches/image/20170317/2869b9218d84bcd05fdad7722cc4baf2.jpg","url":"http://news.wisesz.cc/web/share/regular/320500/323599","red_doit":"1"}
         */

        private ItemsBean items;

        public ItemsBean getItems() {
            return items;
        }

        public void setItems(ItemsBean items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 1
             * title : 公告
             * img_src : http://weizhang.wisesz.cc/attaches/image/20170317/2869b9218d84bcd05fdad7722cc4baf2.jpg
             * url : http://news.wisesz.cc/web/share/regular/320500/323599
             * red_doit : 1
             */

            private String id;
            private String title;
            private String img_src;
            private String url;
            private String red_doit;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg_src() {
                return img_src;
            }

            public void setImg_src(String img_src) {
                this.img_src = img_src;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getRed_doit() {
                return red_doit;
            }

            public void setRed_doit(String red_doit) {
                this.red_doit = red_doit;
            }
        }
    }
}

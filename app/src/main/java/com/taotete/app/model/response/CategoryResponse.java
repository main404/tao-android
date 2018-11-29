package com.taotete.app.model.response;

import java.util.List;

public class CategoryResponse {

    private int code;
    private ResultBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<TopBean> top;

        public List<TopBean> getTop() {
            return top;
        }

        public void setTop(List<TopBean> top) {
            this.top = top;
        }

        public static class TopBean {

            private int id;
            private String name;
            private List<SecondBean> second;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<SecondBean> getSecond() {
                return second;
            }

            public void setSecond(List<SecondBean> second) {
                this.second = second;
            }

            public static class SecondBean {

                private int id;
                private String name;
                private String imgUrl;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }
            }
        }
    }
}

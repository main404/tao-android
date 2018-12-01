package com.taotete.app.model.response;

import java.util.List;

public class CategoryResponse {

    private List<TopBean> category;

    public List<TopBean> getTop() {
        return category;
    }

    public void setTop(List<TopBean> top) {
        this.category = top;
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
            private String img_url;

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
                return img_url;
            }

            public void setImgUrl(String imgUrl) {
                this.img_url = imgUrl;
            }
        }
    }
}

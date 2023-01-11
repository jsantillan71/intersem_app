package com.intersem.sdib.ui.login.models;

public class ResponseLoginModel {
    private int response_flag;
    private String trace;
    private Response response;

    public int getResponse_flag() {
        return response_flag;
    }

    public void setResponse_flag(int response_flag) {
        this.response_flag = response_flag;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response{
        private String token;
        private String user;
        private int user_id;
        private String profile;
        private String company;
        private String date_create;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getDate_create() {
            return date_create;
        }

        public void setDate_create(String date_create) {
            this.date_create = date_create;
        }
    }
}

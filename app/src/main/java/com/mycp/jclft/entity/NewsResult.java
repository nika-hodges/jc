package com.mycp.jclft.entity;

import java.io.Serializable;
import java.util.List;

public class NewsResult implements Serializable {
    public List<NewsBean> news;

    public String result;

    public List<Headers> headers;

    public String totalPageCount;

    public List<Middles> middles;

}
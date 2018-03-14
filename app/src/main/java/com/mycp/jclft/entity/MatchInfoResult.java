package com.mycp.jclft.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Des:
 * Author: leo
 * Date：2018/3/9.
 */

public class MatchInfoResult implements Serializable {
    public List<MatchInfoBean> data;

    public int result;
}

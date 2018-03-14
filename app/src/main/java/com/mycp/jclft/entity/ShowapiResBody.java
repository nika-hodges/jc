package com.mycp.jclft.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Des:
 * Author: leo
 * Date：2018/3/3.
 */

public class ShowapiResBody implements Serializable {
    public List<OpenBean> result;

    public int ret_code;
}

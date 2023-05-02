package com.ivr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ivr.rest.Client;
import com.ivr.util.JsonUtil;

public class Application {

    public static void main(String args[]) {
        Map<String, Object> obj = new HashMap<String, Object>();

        obj.put("id", "hello");
        obj.put("type", "world");

        List<Map<String, Object>> objs = new ArrayList<Map<String, Object>>();
        objs.add(obj);
        objs.add(obj);
        objs.add(obj);
        objs.add(obj);

        System.out.println(JsonUtil.makeJsonString(obj));
        System.out.println(JsonUtil.makeJsonString(objs));

        String urls[] = { "https://github.com/cmhxone", "https://github.com/cmhxone" };

        try {
            System.out.println(Client.request(urls, "GET", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

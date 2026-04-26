package com.smpark.jdbc.lxp.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

public class QueryUtil {

    private static final Map<String, String> QUERY_MAP = new HashMap<String, String>();

    static {
        loadQueries();
    }

    private static void loadQueries() {
        try {
            InputStream inputStream = QueryUtil.class.getClassLoader().getResourceAsStream("query.xml");
            if (inputStream == null) {
                throw new RuntimeException("query.xml 파일을 찾을 수 없습니다.");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(inputStream);

            NodeList queryNodes = document.getElementsByTagName("query");
            for (int i = 0; i < queryNodes.getLength(); i++) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) queryNodes.item(i);
                String key = element.getAttribute("key");
                String value = element.getTextContent().trim().replaceAll("\\s+", " ");
                QUERY_MAP.put(key, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("쿼리 로딩 실패", e);
        }
    }

    public static String getQuery(String key) {
        String query = QUERY_MAP.get(key);
        if (query == null) {
            throw new RuntimeException("해당 key의 쿼리를 찾을 수 없습니다. key=" + key);
        }
        return query;
    }
}



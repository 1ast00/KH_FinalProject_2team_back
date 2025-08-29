package com.vitalog.spring_diet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitalog.spring_diet.dto.FoodNutritionDTO;
import com.vitalog.spring_diet.mapper.FoodMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FoodService {

    private final String SERVICE_KEY = "Nkv11vVle%2BYo80xqKkGzpDuFqx821kQQiFWy5EMcBEwcG%2BbVh8%2FxJPMJS5R3NaoDGmYwXqXDmC%2FqW%2Blrzl7HLg%3D%3D";
    private int pageNo = 1;
    private final int NUM_OF_ROWS = 5;
    //returnType: xml/json
    private String returnType = "json";
    private String prdlstNm;

    private FoodMapper foodMapper;

    public FoodService(FoodMapper foodMapper){
        this.foodMapper = foodMapper;
    }

    //DB에 저장된 데이터를 불러오는 메소드

    //API를 불러와서 요청한 정보를 가공하는 method
    public List<FoodNutritionDTO> foodApiSearch(String searchTxt, int page) {

//        System.out.println("searchTxt in Controller: "+ searchTxt);
//        System.out.println("page in controller"+page);

        pageNo = page;

        //pageNo
        String apiURL = "https://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3";
        apiURL += "?ServiceKey=" + SERVICE_KEY;
        apiURL += "&pageNo=" + pageNo;
        apiURL += "&numOfRows=" + NUM_OF_ROWS;
        apiURL += "&returnType=" + returnType;

        //1. SearchTxt를 UTF-8형태로 encoding 후 URL에 추가
        try {
            prdlstNm = URLEncoder.encode(searchTxt,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        apiURL += "&prdlstNm=" + prdlstNm;

        //2. URL Verification setting

        HttpURLConnection conn = null;
        URL url = null;

        try {
            url = new URL(apiURL);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            //서버와 연결하는 최대시간 설정
            conn.setConnectTimeout(5000);
            //서버 연결후 응답을 기다리는 최대시간
            conn.setReadTimeout(5000);

            InputStream is = null;

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = conn.getInputStream();
            } else{
                is = conn.getErrorStream();
            }

            String responseString = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            responseString = br.lines().collect(Collectors.joining(System.lineSeparator()));
//            System.out.println("responseString: " + responseString);

            if(returnType.equals(returnType)){
                return parseJsonToList(responseString);
            } else {
                return parseXmlToList(responseString);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn != null)
                conn.disconnect();
        }
    }

    //전체 api 데이터 목록 다 받아오는 메소드
    public List<FoodNutritionDTO> foodCarouselSearch(String searchTxt, int page) {

//        System.out.println("searchTxt in Controller: "+ searchTxt);
//        System.out.println("page in controller"+page);

        pageNo = page;

        //pageNo
        String apiURL = "https://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3";
        apiURL += "?ServiceKey=" + SERVICE_KEY;
        apiURL += "&pageNo=" + pageNo;
        apiURL += "&numOfRows=" + 100;
        apiURL += "&returnType=" + returnType;

        //1. SearchTxt를 UTF-8형태로 encoding 후 URL에 추가
        try {
            prdlstNm = URLEncoder.encode(searchTxt,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        apiURL += "&prdlstNm=" + prdlstNm;

        //2. URL Verification setting

        HttpURLConnection conn = null;
        URL url = null;

        try {
            url = new URL(apiURL);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            //서버와 연결하는 최대시간 설정
            conn.setConnectTimeout(5000);
            //서버 연결후 응답을 기다리는 최대시간
            conn.setReadTimeout(5000);

            InputStream is = null;

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                is = conn.getInputStream();
            } else{
                is = conn.getErrorStream();
            }

            String responseString = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            responseString = br.lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("responseString: " + responseString);

            if(returnType.equals(returnType)){
                return parseJsonToList(responseString);
            } else {
                return parseXmlToList(responseString);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(conn != null)
                conn.disconnect();
        }
    }


    //String -> JSON -> list로 parsing(Jackson 이용)
    private List<FoodNutritionDTO> parseJsonToList(String json){

        System.out.println("String json: "+json);

        //빈 리스트를 유사시 반환하기 위해 초기화와 함께 선언
        List<FoodNutritionDTO> resultList = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String,Object> map = mapper.readValue(json, Map.class);
            System.out.println("Map: "+ map);

            Map<String,Object> body = (Map<String, Object>) map.get("body");
            System.out.println("body: "+body);
            Integer totalCount = Integer.parseInt((String) body.get("totalCount"));
            System.out.println("totalCount: "+totalCount);

            //body가 null값인 경우 null pointer Exception이 발생하므로 null값인 경우 빈 배열을 넣어줌: null safe
            if(body == null) return resultList;

            List<Map<String,Object>> foodList = (List<Map<String,Object>>) body.get("items");
            System.out.println("foodList: "+foodList);
            //foodList가 null값인 경우 null pointer Exception이 발생하므로 null값인 경우 빈 배열을 넣어줌: null safe
            if(foodList == null) return resultList;

            for (Map<String,Object> items : foodList) {
                Map<String,Object> item = (Map<String, Object>) items.get("item");
                if(item == null) continue;
                System.out.println("item: "+item);

                FoodNutritionDTO foodDTO = new FoodNutritionDTO();
                //주키:null safe
                String prdlistReportNo = (String) item.get("prdlstReportNo");
                foodDTO.setPrdlstReportNo(prdlistReportNo != null ? prdlistReportNo : "정보없음");

                //객체의 다른 property가 비어있을 시 대비: null safe
                String rnumStr = (String) item.get("rnum");
                foodDTO.setRnum(rnumStr != null ? Integer.parseInt(rnumStr) : 0);

                String productGb = (String) item.get("productGb");
                foodDTO.setProductGb(productGb != null ? productGb : "정보없음");

                String prdlstNm = (String) item.get("prdlstNm");
                foodDTO.setPrdlstNm(prdlstNm != null ? prdlstNm : "정보없음");

                String rawmtrl = (String) item.get("rawmtrl");
                foodDTO.setRawmtrl(rawmtrl != null ? rawmtrl : "정보없음");

                String allergy = (String) item.get("allergy");
                foodDTO.setAllergy(allergy != null ? allergy : "정보없음");

                String nutrient = (String) item.get("nutrient");
                foodDTO.setNutrient(nutrient != null ? nutrient : "정보없음");

                String prdkind = (String) item.get("prdkind");
                foodDTO.setPrdkind(prdkind != null ? prdkind : "정보없음");

                String manufacture = (String) item.get("manufacture");
                foodDTO.setManufacture(manufacture != null ? manufacture: "정보없음");
                
                String imgurl1 = (String) item.get("imgurl1");
                foodDTO.setImgurl1(imgurl1 != null? imgurl1 : "정보없음");

                String imgurl2 = (String) item.get("imgurl2");
                foodDTO.setImgurl2(imgurl2 != null? imgurl2: "정보없음");
                
                foodDTO.setTotalCount(totalCount != null? totalCount: 0);

                resultList.add(foodDTO);
            }

            System.out.println("resultList: "+ resultList);

            return resultList;
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    //String -> xml -> list로 parsing
    private List<FoodNutritionDTO> parseXmlToList(String xml){
        List<FoodNutritionDTO> list = new ArrayList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            NodeList items = doc.getElementsByTagName("item");
            System.out.println("items: "+items);

            for(int i = 0; i < items.getLength(); i++){
                Element e = (Element) items.item(i);

                FoodNutritionDTO foodDTO = new FoodNutritionDTO();

                //주키
                foodDTO.setPrdlstReportNo(getTagValue(e,"prdlstReportNo"));

                foodDTO.setRnum(Integer.parseInt(getTagValue(e,"rnum")));
                foodDTO.setProductGb(getTagValue(e,"productGb"));
                foodDTO.setPrdlstNm(getTagValue(e, "prdlistNm"));
                foodDTO.setRawmtrl(getTagValue(e,"rawmtrl"));
                foodDTO.setAllergy(getTagValue(e,"allergy"));
                foodDTO.setNutrient(getTagValue(e,"nutrient"));
                foodDTO.setPrdkind(getTagValue(e,"prdkind"));
                foodDTO.setManufacture(getTagValue(e,"manufacture"));
                foodDTO.setImgurl1(getTagValue(e,"imgurl1"));
                foodDTO.setImgurl2(getTagValue(e,"imgurl2"));

                list.add(foodDTO);
            }

        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    //XMl element에서 텍스트값 가져옴
    private String getTagValue(Element element, String tagName){
        NodeList nodeList = element.getElementsByTagName(tagName);
        if(nodeList.getLength() > 0 && nodeList.item(0).getFirstChild()!=null){
            return nodeList.item(0).getTextContent();
        } else {
            return null;
        }
    }
}
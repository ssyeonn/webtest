package com.utility;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utility {

	public static String checkNull(String str) {
		if (str == null) {
			str = "";
		}

		return str;
	}

	/**
	 * @param totalRecord   전체 레코드수
	 * @param nowPage       현재 페이지
	 * @param recordPerPage 페이지당 레코드 수
	 * @param col           검색 컬럼
	 * @param word          검색어
	 * @param url           이동할 페이지
	 * @return 페이징 생성 문자열
	 */
	public static String paging(int totalRecord, int nowPage, int recordPerPage, String col, String word, String url) {
		int pagePerBlock = 5; // 블럭당 페이지 수
		int totalPage = (int) (Math.ceil((double) totalRecord / recordPerPage)); // 전체 페이지
		int totalGrp = (int) (Math.ceil((double) totalPage / pagePerBlock));// 전체 그룹
		int nowGrp = (int) (Math.ceil((double) nowPage / pagePerBlock)); // 현재 그룹
		int startPage = ((nowGrp - 1) * pagePerBlock) + 1; // 특정 그룹의 페이지 목록 시작
		int endPage = (nowGrp * pagePerBlock); // 특정 그룹의 페이지 목록 종료

		StringBuffer str = new StringBuffer();
		str.append("<ul class='pagination justify-content-center'> ");
		int _nowPage = (nowGrp - 1) * pagePerBlock; // 10개 이전 페이지로 이동
		if (nowGrp >= 2) {
			str.append("<li class='page-item'><a class='page-link' href='" + url + "?col=" + col + "&word=" + word
					+ "&nowPage=" + _nowPage + "'>이전</A></li>");
		}

		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}

			if (nowPage == i) {
				str.append("<li class='page-item active'><a class='page-link' href=#>" + i + "</a></li>");
			} else {
				str.append("<li class='page-item'><a class='page-link' href='" + url + "?col=" + col + "&word=" + word
						+ "&nowPage=" + i + "'>" + i + "</A></li>");
			}
		}

		_nowPage = (nowGrp * pagePerBlock) + 1; // 10개 다음 페이지로 이동
		if (nowGrp < totalGrp) {
			str.append("<li class='page-item'><a class='page-link' href='" + url + "?col=" + col + "&word=" + word
					+ "&nowPage=" + _nowPage + "'>다음</a></li>");
		}
		str.append("</ul>");

		return str.toString();
	}
	
	/**
     * 오늘,어제,그제 날짜 가져오기
     * @return List- 날짜들 저장
     * SimpleDateFormat("yyyy-MM-dd") 
     */
    public static List<String> getDay(){
        List<String> list = new ArrayList<String>();
        
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar cal = Calendar.getInstance();
        for(int j = 0; j < 3; j++){
            list.add(sd.format(cal.getTime()));
            cal.add(Calendar.DATE, -1);
        }
        
        return list;
    }
    
    /**
     * 등록날짜와 오늘,어제,그제날짜와 비교
     * @param wdate - 등록날짜
     * @return - true:오늘,어제,그제중 등록날짜와 같음
     *           false:오늘,어제,그제 날짜가 등록날짜와 다 다름
     */
    public static boolean compareDay(String wdate){
        boolean flag = false;
        
        String date = wdate.substring(0, 10);
        List<String> list = getDay();
        if(date.equals(list.get(0)) 
           || date.equals(list.get(1))
           || date.equals(list.get(2))){
            flag = true;
        }         
        return flag;
    }
    
    public static boolean compareDay24(String wdate){
        boolean flag = false;
        
        try {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	
        	Calendar compare_time = Calendar.getInstance();        	
			compare_time.setTime(formatter.parse(wdate));
//			System.out.println("wdate: "+formatter.format(compare_time.getTime()));
	        
			Calendar stand_time = Calendar.getInstance();
			stand_time.add(Calendar.HOUR, -24);
//	        System.out.println("time: " +formatter.format(stand_time.getTime()));
	        
//	        System.out.println("작성일: " +formatter.format(compare_time.getTime()));
//	        System.out.println("비교일: " +formatter.format(stand_time.getTime()));
//	        System.out.println(compare_time.compareTo(stand_time));
	        if(compare_time.compareTo(stand_time)>0) {
	        	flag = true;
	        }
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return flag;
    }
}

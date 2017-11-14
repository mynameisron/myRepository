package com.company.study.controller;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.study.service.DictionaryService;
 
@Controller
public class DictionaryController {
 
    @Autowired
    DictionaryService dictionaryService;
 
    //사전내용 리스트 조회
    @RequestMapping(value = "/dictionary/list")
    public String dictionaryList(@RequestParam Map<String, Object> paramMap, Model model) {
 
        //조회 하려는 페이지
        int startPage = (paramMap.get("startPage")!=null?Integer.parseInt(paramMap.get("startPage").toString()):1);
        //한페이지에 보여줄 리스트 수
        int visiblePages = (paramMap.get("visiblePages")!=null?Integer.parseInt(paramMap.get("visiblePages").toString()):10);
        //일단 전체 건수를 가져온다.
        int totalCnt = dictionaryService.getContentCnt(paramMap);
 
 
        //아래 1,2는 실제 개발에서는 class로 빼준다. (여기서는 이해를 위해 직접 적음)
        //1.하단 페이지 네비게이션에서 보여줄 리스트 수를 구한다.
        BigDecimal decimal1 = new BigDecimal(totalCnt);
        BigDecimal decimal2 = new BigDecimal(visiblePages);
        BigDecimal totalPage = decimal1.divide(decimal2, 0, BigDecimal.ROUND_UP);
 
        int startLimitPage = 0;
        //2.mysql limit 범위를 구하기 위해 계산
        if(startPage==1){
            startLimitPage = 0;
        }else{
            startLimitPage = (startPage-1)*visiblePages;
        }
 
        paramMap.put("start", startLimitPage);
        paramMap.put("end", visiblePages);
 
        //jsp 에서 보여줄 정보 추출
        model.addAttribute("startPage", startPage+"");//현재 페이지      
        model.addAttribute("totalCnt", totalCnt);//전체 게시물수
        model.addAttribute("totalPage", totalPage);//페이지 네비게이션에 보여줄 리스트 수
        model.addAttribute("dictionarydList", dictionaryService.getContentList(paramMap));//검색
 
        return "dictionaryList";
 
    }
 
    //게시글 상세 보기
    @RequestMapping(value = "/dictionary/view")
    public String dictionaryView(@RequestParam Map<String, Object> paramMap, Model model) {
 
        model.addAttribute("dictionaryView", dictionaryService.getContentView(paramMap));
 
        return "dictionaryView";
 
    }
 
    //사전내용 등록 및 수정
    @RequestMapping(value = "/dictionary/edit")
    public String dictionaryEdit(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {
 
        //Referer 검사
        String Referer = request.getHeader("referer");
 
        if(Referer!=null){//URL로 직접 접근 불가
            if(paramMap.get("id") != null){ //게시글 수정
                if(Referer.indexOf("/dictionary/view")>-1){
 
                    //정보를 가져온다.
                    model.addAttribute("dictionaryView", dictionaryService.getContentView(paramMap));
                    return "dictionaryEdit";
                }else{
                    return "redirect:/dictionary/list";
                }
            }else{ //게시글 등록
                if(Referer.indexOf("/dictionary/list")>-1){
                    return "dictionaryEdit";
                }else{
                    return "redirect:/dictionary/list";
                }
            }
        }else{
            return "redirect:/dictionary/list";
        }
 
    }
 
    //AJAX 호출 (사전내용 등록)
    @RequestMapping(value="/dictionary/save", method=RequestMethod.POST)
    @ResponseBody
    public Object dictionarySave(@RequestParam Map<String, Object> paramMap) {
 
        //리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //정보입력
        int result = dictionaryService.regContent(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("message", "등록에 성공 하였습니다.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "등록에 실패 하였습니다.");
        }
 
        return retVal;
 
    }
 
    //AJAX 호출 (사전내용 삭제)
    @RequestMapping(value="/dictionary/del", method=RequestMethod.POST)
    @ResponseBody
    public Object dictionaryDel(@RequestParam Map<String, Object> paramMap) {
 
        //리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //정보입력
        int result = dictionaryService.delDictionary(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "패스워드를 확인해주세요.");
        }
 
        return retVal;
 
    }
 
    //AJAX 호출 (사전내용 확인)
    @RequestMapping(value="/dictionary/check", method=RequestMethod.POST)
    @ResponseBody
    public Object dictionaryCheck(@RequestParam Map<String, Object> paramMap) {
 
        //리턴값
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //정보입력
        int result = dictionaryService.getDictionaryCheck(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "사전내용을 확인해주세요.");
        }
 
        return retVal;
 
    }
 
 
 
}

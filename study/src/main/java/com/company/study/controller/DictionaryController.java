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
 
    //�������� ����Ʈ ��ȸ
    @RequestMapping(value = "/dictionary/list")
    public String dictionaryList(@RequestParam Map<String, Object> paramMap, Model model) {
 
        //��ȸ �Ϸ��� ������
        int startPage = (paramMap.get("startPage")!=null?Integer.parseInt(paramMap.get("startPage").toString()):1);
        //���������� ������ ����Ʈ ��
        int visiblePages = (paramMap.get("visiblePages")!=null?Integer.parseInt(paramMap.get("visiblePages").toString()):10);
        //�ϴ� ��ü �Ǽ��� �����´�.
        int totalCnt = dictionaryService.getContentCnt(paramMap);
 
 
        //�Ʒ� 1,2�� ���� ���߿����� class�� ���ش�. (���⼭�� ���ظ� ���� ���� ����)
        //1.�ϴ� ������ �׺���̼ǿ��� ������ ����Ʈ ���� ���Ѵ�.
        BigDecimal decimal1 = new BigDecimal(totalCnt);
        BigDecimal decimal2 = new BigDecimal(visiblePages);
        BigDecimal totalPage = decimal1.divide(decimal2, 0, BigDecimal.ROUND_UP);
 
        int startLimitPage = 0;
        //2.mysql limit ������ ���ϱ� ���� ���
        if(startPage==1){
            startLimitPage = 0;
        }else{
            startLimitPage = (startPage-1)*visiblePages;
        }
 
        paramMap.put("start", startLimitPage);
        paramMap.put("end", visiblePages);
 
        //jsp ���� ������ ���� ����
        model.addAttribute("startPage", startPage+"");//���� ������      
        model.addAttribute("totalCnt", totalCnt);//��ü �Խù���
        model.addAttribute("totalPage", totalPage);//������ �׺���̼ǿ� ������ ����Ʈ ��
        model.addAttribute("dictionarydList", dictionaryService.getContentList(paramMap));//�˻�
 
        return "dictionaryList";
 
    }
 
    //�Խñ� �� ����
    @RequestMapping(value = "/dictionary/view")
    public String dictionaryView(@RequestParam Map<String, Object> paramMap, Model model) {
 
        model.addAttribute("dictionaryView", dictionaryService.getContentView(paramMap));
 
        return "dictionaryView";
 
    }
 
    //�������� ��� �� ����
    @RequestMapping(value = "/dictionary/edit")
    public String dictionaryEdit(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {
 
        //Referer �˻�
        String Referer = request.getHeader("referer");
 
        if(Referer!=null){//URL�� ���� ���� �Ұ�
            if(paramMap.get("id") != null){ //�Խñ� ����
                if(Referer.indexOf("/dictionary/view")>-1){
 
                    //������ �����´�.
                    model.addAttribute("dictionaryView", dictionaryService.getContentView(paramMap));
                    return "dictionaryEdit";
                }else{
                    return "redirect:/dictionary/list";
                }
            }else{ //�Խñ� ���
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
 
    //AJAX ȣ�� (�������� ���)
    @RequestMapping(value="/dictionary/save", method=RequestMethod.POST)
    @ResponseBody
    public Object dictionarySave(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�����Է�
        int result = dictionaryService.regContent(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (�������� ����)
    @RequestMapping(value="/dictionary/del", method=RequestMethod.POST)
    @ResponseBody
    public Object dictionaryDel(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�����Է�
        int result = dictionaryService.delDictionary(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�н����带 Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (�������� Ȯ��)
    @RequestMapping(value="/dictionary/check", method=RequestMethod.POST)
    @ResponseBody
    public Object dictionaryCheck(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�����Է�
        int result = dictionaryService.getDictionaryCheck(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "���������� Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
 
 
 
}

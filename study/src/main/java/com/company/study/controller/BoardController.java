package com.company.study.controller;
 
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
 
import com.company.study.service.BoardService;
 
@Controller
public class BoardController {
 
    @Autowired
    BoardService boardService;
 
    //�Խñ� ����Ʈ ��ȸ
    @RequestMapping(value = "/board/list")
    public String boardList(@RequestParam Map<String, Object> paramMap, Model model) {
 
        //��ȸ �Ϸ��� ������
        int startPage = (paramMap.get("startPage")!=null?Integer.parseInt(paramMap.get("startPage").toString()):1);
        //���������� ������ ����Ʈ ��
        int visiblePages = (paramMap.get("visiblePages")!=null?Integer.parseInt(paramMap.get("visiblePages").toString()):10);
        //�ϴ� ��ü �Ǽ��� �����´�.
        int totalCnt = boardService.getContentCnt(paramMap);
 
 
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
        model.addAttribute("boardList", boardService.getContentList(paramMap));//�˻�
 
        return "boardList";
 
    }
 
    //�Խñ� �� ����
    @RequestMapping(value = "/board/view")
    public String boardView(@RequestParam Map<String, Object> paramMap, Model model) {
 
        model.addAttribute("replyList", boardService.getReplyList(paramMap));
        model.addAttribute("boardView", boardService.getContentView(paramMap));
 
        return "boardView";
 
    }
 
    //�Խñ� ��� �� ����
    @RequestMapping(value = "/board/edit")
    public String boardEdit(HttpServletRequest request, @RequestParam Map<String, Object> paramMap, Model model) {
 
        //Referer �˻�
        String Referer = request.getHeader("referer");
 
        if(Referer!=null){//URL�� ���� ���� �Ұ�
            if(paramMap.get("id") != null){ //�Խñ� ����
                if(Referer.indexOf("/board/view")>-1){
 
                    //������ �����´�.
                    model.addAttribute("boardView", boardService.getContentView(paramMap));
                    return "boardEdit";
                }else{
                    return "redirect:/board/list";
                }
            }else{ //�Խñ� ���
                if(Referer.indexOf("/board/list")>-1){
                    return "boardEdit";
                }else{
                    return "redirect:/board/list";
                }
            }
        }else{
            return "redirect:/board/list";
        }
 
    }
 
    //AJAX ȣ�� (�Խñ� ���)
    @RequestMapping(value="/board/save", method=RequestMethod.POST)
    @ResponseBody
    public Object boardSave(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�����Է�
        int result = boardService.regContent(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (�Խñ� ����)
    @RequestMapping(value="/board/del", method=RequestMethod.POST)
    @ResponseBody
    public Object boardDel(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�����Է�
        int result = boardService.delBoard(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�н����带 Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (�Խñ� �н����� Ȯ��)
    @RequestMapping(value="/board/check", method=RequestMethod.POST)
    @ResponseBody
    public Object boardCheck(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("password").toString(), null);
        paramMap.put("password", password);
 
        //�����Է�
        int result = boardService.getBoardCheck(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "�н����带 Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (��� ���)
    @RequestMapping(value="/board/reply/save", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplySave(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�����Է�
        int result = boardService.regReply(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
            retVal.put("reply_id", paramMap.get("reply_id"));
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "��Ͽ� ���� �Ͽ����ϴ�.");
        }
 
        return retVal;
 
    }
 
    //AJAX ȣ�� (��� ����)
    @RequestMapping(value="/board/reply/del", method=RequestMethod.POST)
    @ResponseBody
    public Object boardReplyDel(@RequestParam Map<String, Object> paramMap) {
 
        //���ϰ�
        Map<String, Object> retVal = new HashMap<String, Object>();
 
        //�н����� ��ȣȭ
        ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        String password = encoder.encodePassword(paramMap.get("reply_password").toString(), null);
        paramMap.put("reply_password", password);
 
        //�����Է�
        int result = boardService.delReply(paramMap);
 
        if(result>0){
            retVal.put("code", "OK");
        }else{
            retVal.put("code", "FAIL");
            retVal.put("message", "������ �����߽��ϴ�. �н����带 Ȯ�����ּ���.");
        }
 
        return retVal;
 
    }
 
 
}
package corelin.web.bigstudent.controller;

import corelin.web.bigstudent.index.MainPlayerOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author sgl
 * @Date 2018-05-15 14:04
 */
@RestController
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * 实现单文件的上传
     * @param file
     * @param response
     * @return
     */
    @RequestMapping(value = "/gotoAction", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name ,  HttpServletResponse response) {
        //没有选择文件
        if (file == null || file.getOriginalFilename().isEmpty()){
            response.setContentType("text/html;charset=gb2312");
            try {
                PrintWriter out = response.getWriter();
                out.write("<script language=\"javascript\">alert('请上传文件！');window.location.href='/'</script>");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        //没有选择姓名学号
        if (name.isEmpty()){
            try {
                PrintWriter out = response.getWriter();
                out.write("<script language=\"javascript\">alert('请选择你的名称！');window.location.href='/'</script>");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        System.out.println("fileName："+file.getOriginalFilename());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(new Date());
        String storePath = System.getProperty("user.dir") + File.separator + dateNowStr;//存放我们上传的文件路径
        File mrFile = new File(storePath);
        if (!mrFile.exists()){
            mrFile.mkdirs();
        }
        String oldFileName = file.getOriginalFilename();
        String fileSuffix = oldFileName.substring(oldFileName.lastIndexOf("."));
        String fileName = name + (".png".equals(fileSuffix) ? ".jpg" : fileSuffix);
       // String path=dateNowStr +fileName;

        File newFile=new File(storePath , fileName);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传完成";
    }


    //第一类是ModelAndView就这一种方法  第二类是String三种方法
    @RequestMapping("/")
	public ModelAndView userInfoView() {
		ModelAndView mv = new ModelAndView("index");
        mv.addObject("postFile", "上传文件");
        //获取部门列表
        List<MainPlayerOption> departmentList = new ArrayList<MainPlayerOption>();
        departmentList.add(new MainPlayerOption("042131101", "单英楠"));
        mv.addObject("departmentList" , departmentList);
		return mv;
	}

/*    @RequestMapping("/modelcyTest")
    public Model modelcyTest(Model model){
        model.addAttribute("name","chaoying");
        return model;
    }*/


}


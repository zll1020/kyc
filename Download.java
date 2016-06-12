package cn.cust.kyc.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.FieldBean;
import cn.cust.kyc.util.FieldsControl;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.RecordSetting;
import cn.cust.kyc.vo.Member;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.PFile;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class Download extends ActionSupport{
    private  String innerCode;
	   
	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}
	//下载文件原始存放路径   
	private final  String DOWNLOADFILEPATH= ServletActionContext.getServletContext().getRealPath("KycFile/"+innerCode);  
    //     文件名参数变量   ???
	 private String fileName;
	 //项目内部编号  
	  
	public String getFileName() {   
             return fileName;   
	   }  
	   
	   public void setFileName(String fileName) {   
		        this.fileName = fileName;   
	   }   
		  
	   //从下载文件原始存放路径读取得到文件输出流   
	    public InputStream getDownloadFile() {   
		        return ServletActionContext.getServletContext().getResourceAsStream(DOWNLOADFILEPATH+fileName);   
	    }   
	    //如果下载文件名为中文，进行字符编码转换   
	    public String getDownloadChineseFileName() {   
	        String downloadChineseFileName = fileName;  
	        try {   
	              downloadChineseFileName = new String(downloadChineseFileName.getBytes(), "ISO8859-1");   
           } catch (UnsupportedEncodingException e) {   
                  e.printStackTrace();   
	       }   
	        return downloadChineseFileName;   
	    }   
	  
		    public String execute() {   
		        return SUCCESS;   
	    }   

}

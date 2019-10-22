package com.qf.listener;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qf.entity.NoteCode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author DingYuHui
 * @Date 2019/10/19
 */
@Component
public class RabbitNoteCodeListener {
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";




    @RabbitListener(queues = "notecode_queue")
    public void sendNoteCode(NoteCode noteCode){
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        System.out.println("需要发送的短信验证码：" + noteCode);
        DefaultProfile profile = DefaultProfile.getProfile(noteCode.getRegionId(), noteCode.getAccessKeyID(), noteCode.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setAction("SendSms");

        request.putQueryParameter("RegionId", noteCode.getRegionId());
        request.setVersion(noteCode.getVersion());
        request.putQueryParameter("PhoneNumbers", noteCode.getPhoneNumbers());
        request.putQueryParameter("SignName", noteCode.getSignName());
        request.putQueryParameter("TemplateCode", noteCode.getTemplateCode());
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + noteCode.getCode() + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            System.out.println(noteCode.getCode());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}

package com.yuchengtech.core.utils.push;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yuchengtech.mobile.console.common.Constants;
import com.yuchengtech.mobile.console.entity.push.TblPushinfo;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;

import org.codehaus.jackson.map.ObjectMapper;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
@Component
public class PushUtils  {
	
	protected static final Logger LOG = LoggerFactory
			.getLogger(PushUtils.class);
	
	private static final String appKey=Constants.appkey;
	private static final String masterSecret=Constants.masterSecret;
//
	public static void main(String[] ars){
//		PushUtils pushInfomation=new PushUtils();
//		try {
////			pushInfomation.SendAllPush("测试内容");
//		} catch (APIConnectionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (APIRequestException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	
	public void SendPush(TblPushinfo tblPushinfo) throws APIConnectionException, APIRequestException  {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
		PushUtils tpushAndroid=new PushUtils();
		PushPayload payload;
		if(tblPushinfo.getPushtype().equalsIgnoreCase("1")){
			 payload = tpushAndroid.buildPushObject_ios(tblPushinfo.getPushinfo());
		}else if (tblPushinfo.getPushtype().equalsIgnoreCase("2")) {
			 payload = tpushAndroid.buildPushObject_android(tblPushinfo.getPushinfo());
		}
		else {
			 payload = tpushAndroid.buildPushObject_android_and_ios(tblPushinfo.getPushinfo());	
		}
		try {
			
			PushResult result = jpushClient.sendPush(payload);
			tblPushinfo.setMasgId(result.msg_id+"");
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
			throw e;
		} catch (APIRequestException e) {
			LOG.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
			throw e;
		}
	}
	
	
	
	private PushPayload buildPushObject_ios(String pushinfo) {
		return PushPayload.newBuilder().setPlatform(Platform.ios())
				.setAudience(Audience.all())
				.setNotification(Notification.newBuilder().setAlert(pushinfo).build())
				.build();
	}



	private PushPayload buildPushObject_android(String pushinfo) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.all())
				.setNotification(Notification.newBuilder().setAlert(pushinfo).build())
				.build();
	}

	


//	private PushPayload buildPushObject_android_alias_alertWithTitle(String ALIAS, String ALERT, String TITLE) {
//		return PushPayload.newBuilder().setPlatform(Platform.android())
//				.setAudience(Audience.alias(ALIAS))
//				.setNotification(Notification.android(ALERT, TITLE, null))
//				.build();
//	}
//
//	private PushPayload buildPushObject_android_tag_alertWithTitle(String TAG, String ALERT, String TITLE) {
//		return PushPayload.newBuilder().setPlatform(Platform.android())
//				.setAudience(Audience.tag(TAG))
//				.setNotification(Notification.android(ALERT, TITLE, null))
//				.build();
//	}
//	
//	private PushPayload buildPushObject_android_all_alertWithTitle(String ALERT, String TITLE) {
//		return PushPayload.newBuilder().setPlatform(Platform.android())
//				.setAudience(Audience.all())
//				.setNotification(Notification.android(ALERT, TITLE, null))
//				.build();
//	}

//	private PushPayload buildPushObject_android_REGISTRATIONID_alertWithTitle(Collection<String> regiester, String ALERT, String TITLE) {
//		return PushPayload.newBuilder().setPlatform(Platform.android())
//				.setAudience(Audience.registrationId(regiester))
//				.setNotification(Notification.android(ALERT, TITLE, null))
//				.build();
//	}


	public  PushPayload buildPushObject_android_and_ios(String alert) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.all())
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(alert)
								.addPlatformNotification(
										AndroidNotification.newBuilder().build())
								.addPlatformNotification(
										IosNotification.newBuilder().build())
								.build()).build();
	}

	private class getResult{
		private String msg_id;
		private String sendno;
		public String getMsg_id() {
			return msg_id;
		}
		public void setMsg_id(String msg_id) {
			this.msg_id = msg_id;
		}
		public String getSendno() {
			return sendno;
		}
		public void setSendno(String sendno) {
			this.sendno = sendno;
		}
	}
	
//	public  PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//	return PushPayload
//			.newBuilder()
//			.setPlatform(Platform.android_ios())
//			.setAudience(
//					Audience.newBuilder()
//							.addAudienceTarget(
//									AudienceTarget.tag("tag1", "tag2"))
//							.addAudienceTarget(
//									AudienceTarget
//											.alias("alias1", "alias2"))
//							.build())
//			.setMessage(
//					Message.newBuilder().setMsgContent(MSG_CONTENT)
//							.addExtra("from", "JPush").build()).build();
//}
//
//public  PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
//	return PushPayload
//			.newBuilder()
//			.setPlatform(Platform.ios())
//			.setAudience(Audience.tag_and("tag1", "tag_all"))
//			.setNotification(
//					Notification
//							.newBuilder()
//							.addPlatformNotification(
//									IosNotification.newBuilder()
//											.setAlert(ALERT).setBadge(5)
//											.setSound("happy")
//											.addExtra("from", "JPush")
//											.build()).build())
//			.setMessage(Message.content(MSG_CONTENT))
//			.setOptions(
//					Options.newBuilder().setApnsProduction(true).build())
//			.build();
//}
	
	
}
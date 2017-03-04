package com.dataagg.account.client;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by watano on 2017/2/20.
 */
@FeignClient(name = "wechatService", url = WeChatServiceClient.BaseUrl)
@Headers("Accept: application/json;charset=UTF-8")
public interface WeChatServiceClient {
	public final Logger LOG = LoggerFactory.getLogger(WeChatServiceClient.class);
	public static String BaseUrl = "https://api.weixin.qq.com";

	public String getAppID();

	public String getAppSecret();

	public default String getAuthorizeUrl(String redirect_uri, String state) {
		String url = BaseUrl + "/sns/oauth2/authorize?";
		if (getAppID() == null) {
			LOG.error("Error: AppID is null!");
		} else {
			url += "appid=" + getAppID();
		}
		if (redirect_uri == null) {
			LOG.error("Error: redirect_uri is null!");
		} else {
			url += "&redirect_uri=" + redirect_uri;
		}
		url += "&response_type=code";
		url += "&scope=snsapi_userinfo";

		if (state == null) {
			LOG.error("Error: state is null!");
		} else {
			url += "&state=" + state;
		}
		url += "#wechat_redirect";
		return url;
	}

	@RequestLine("GET /sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code")
	public Object accessToken(@Param("appid") String appID, @Param("secret") String secret, @Param("code") String code);

	@RequestLine("GET /sns/oauth2/refresh_token?appid={appid}&grant_type=refresh_token&refresh_token={refresh_token}")
	public Object refreshToken(@Param("appid") String appID, @Param("refresh_token") String refresh_token);

	/**
	 * 获取用户基本信息（包括UnionID机制）
	 * http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
	 *
	 * @param access_token
	 * @param openid
	 * @return subscribe    用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 * openid	用户的标识，对当前公众号唯一
	 * nickname	用户的昵称
	 * sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 * city	用户所在城市
	 * country	用户所在国家
	 * province	用户所在省份
	 * language	用户的语言，简体中文为zh_CN
	 * headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 * subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 * unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 * remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 * groupid	用户所在的分组ID
	 */
	@RequestLine("GET /cgi-bin/user/info?access_token={access_token}&openid={openid}&lang=zh_CN")
	public Object userInfo(@Param("access_token") String access_token, @Param("openid") String openid);


	/**
	 * 批量获取用户基本信息
	 * http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
	 *
	 * @param access_token
	 * @param openids
	 *  body { "user_list": [
	 *                     {"openid": "otvxTs4dckWG7imySrJd6jSi0CWE",
	 *                     "lang": "zh-CN" },
	 *                     {"openid": "otvxTs_JZ6SEiP0imdhpi50fuSZg",
	 *                     "lang": "zh-CN" }
	 *                     ]}
	 *                     openid	是	用户的标识，对当前公众号唯一
	 *                     lang	否	国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
	 * @return subscribe    用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，只有openid和UnionID（在该公众号绑定到了微信开放平台账号时才有）。
	 * openid	用户的标识，对当前公众号唯一
	 * nickname	用户的昵称
	 * sex	用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 * city	用户所在城市
	 * country	用户所在国家
	 * province	用户所在省份
	 * language	用户的语言，简体中文为zh_CN
	 * headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 * subscribe_time	用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 * unionid	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 * remark	公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	 * groupid	用户所在的分组ID
	 */
	//FIXME body template
	@RequestLine("POST /cgi-bin/user/info/batchget?access_token={access_token}")
	@Body("{ \"user_list\": [" +
			"{\"openid\": \"{openids[0]}\", \"lang\": \"zh-CN\" }," +
			"{\"openid\": \"{openids[1]}\", \"lang\": \"zh-CN\" }" +
			"]}")
	public Object batchGetUserInfo(@Param("access_token") String access_token, String[] openids);

	/**
	 * 获取用户列表
	 *
	 * @param access_token 调用接口凭证
	 * @param next_openid  第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 正确时返回JSON数据包：
	 * {"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
	 * 参数	说明
	 * total	关注该公众账号的总用户数
	 * count	拉取的OPENID个数，最大值为10000
	 * data	列表数据，OPENID的列表
	 * next_openid	拉取列表的最后一个用户的OPENID
	 */
	@RequestLine("GET /cgi-bin/user/get?access_token={access_token}&next_openid={next_openid}")
	public Object getUser(@Param("access_token") String access_token, @Param("next_openid") String next_openid);

	@RequestLine("GET /cgi-bin/user/get?access_token={access_token}")
	public Object getUser(@Param("access_token") String access_token);
}

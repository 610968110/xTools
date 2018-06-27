package lbx.xtools;

import com.google.gson.annotations.SerializedName;

/**
 * @author lbx
 * @date 2018/6/3.
 * GsonBean 登录获取app各种配置信息 包括gzb/sns的ip 账号密码等
 * 具体的字段注释 跟进get方法去找
 */

public class LoginMainBean {

    @SerializedName("gzbAppKey")
    private String gzbAppKey;
    @SerializedName("gzbAppSecret")
    private String gzbAppSecret;
    @SerializedName("gzbIp")
    private String gzbIp;
    @SerializedName("gzbPort")
    private String gzbPort;
    @SerializedName("lawHtmlUrl")
    private String lawHtmlUrl;
    @SerializedName("newHtmlUrl")
    private String newHtmlUrl;
    @SerializedName("snsCrowdUrl")
    private String snsCrowdUrl;
    @SerializedName("snsIp")
    private String snsIp;
    @SerializedName("snsToken")
    private String snsToken;
    @SerializedName("snsUserName")
    private String snsUserName;
    @SerializedName("snsUserPassword")
    private String snsUserPassword;
    @SerializedName("noticeDynamicHtmlUrl")
    private String noticeDynamicUrl;
    @SerializedName("noticeSystemHtmlUrl")
    private String noticeSystemUrl;
    @SerializedName("noticeUserHtmlUrl")
    private String noticeUserUrl;
    @SerializedName("circleFavoriteHtmlUrl")
    private String circleFavoriteUrl;
    @SerializedName("circleHotHtmlUrl")
    private String circleHotUrl;
    @SerializedName("circleBaseHtmlUrl")
    private String circleBaseUrl;
    @SerializedName("situationUrl")
    private String situationUrl;
    /**
     * 人像对比
     */
    @SerializedName("imageCompareHtmlUrl")
    private String faceContrast;
    /**
     * 在逃核查
     */
    @SerializedName("runningHtmlUrl")
    private String wscapedConvictExamination;
    /**
     * 常住核查
     */
    @SerializedName("czrkHtmlUrl")
    private String czrkHtmlUrl;
    /**
     * 违章核查
     */
    @SerializedName("wzhcHtmlUrl")
    private String wzhcHtmlUrl;
    /**
     * 车辆核查
     */
    @SerializedName("clhcHtmlUrl")
    private String clhcHtmlUrl;
    /**
     * 涉毒人员
     */
    @SerializedName("poisonHtmlUrl")
    private String poisonHtmlUrl;
    /**
     * 违法犯罪
     */
    @SerializedName("wffzHtmlUrl")
    private String wffzHtmlUrl;


    public String getGzbAppKey() {
        return gzbAppKey;
    }

    public String getGzbAppSecret() {
        return gzbAppSecret;
    }

    public String getGzbIp() {
        return gzbIp;
    }

    public String getGzbPort() {
        return gzbPort;
    }

    public String getLawHtmlUrl() {
        return lawHtmlUrl;
    }

    public String getNewHtmlUrl() {
        return newHtmlUrl;
    }

    public String getSnsCrowdUrl() {
        return snsCrowdUrl;
    }

    public String getSnsIp() {
        return snsIp;
    }

    public String getSnsToken() {
        return snsToken;
    }

    public String getSnsUserName() {
        return snsUserName;
    }

    public String getSnsUserPassword() {
        return snsUserPassword;
    }

    public String getNoticeDynamicUrl() {
        return noticeDynamicUrl;
    }

    public String getNoticeSystemUrl() {
        return noticeSystemUrl;
    }

    public String getNoticeUserUrl() {
        return noticeUserUrl;
    }

    public String getCircleFavoriteUrl() {
        return circleFavoriteUrl;
    }

    public String getCircleHotUrl() {
        return circleHotUrl;
    }

    public String getCircleBaseUrl() {
        return circleBaseUrl;
    }

    public String getSituationUrl() {
        return situationUrl;
    }

    public String getFaceContrast() {
        return faceContrast;
    }

    public String getWzhcHtmlUrl() {
        return wzhcHtmlUrl;
    }

    public String getClhcHtmlUrl() {
        return clhcHtmlUrl;
    }

    public String getPoisonHtmlUrl() {
        return poisonHtmlUrl;
    }

    public String getWffzHtmlUrl() {
        return wffzHtmlUrl;
    }

    public String getCzrkHtmlUrl() {
        return czrkHtmlUrl;
    }

    public String getWscapedConvictExamination() {
        return wscapedConvictExamination;
    }

    @Override
    public String toString() {
        return "LoginMainBean{" +
                "gzbAppKey='" + gzbAppKey + '\'' +
                ", gzbAppSecret='" + gzbAppSecret + '\'' +
                ", gzbIp='" + gzbIp + '\'' +
                ", gzbPort='" + gzbPort + '\'' +
                ", lawHtmlUrl='" + lawHtmlUrl + '\'' +
                ", newHtmlUrl='" + newHtmlUrl + '\'' +
                ", snsCrowdUrl='" + snsCrowdUrl + '\'' +
                ", snsIp='" + snsIp + '\'' +
                ", snsToken='" + snsToken + '\'' +
                ", snsUserName='" + snsUserName + '\'' +
                ", snsUserPassword='" + snsUserPassword + '\'' +
                ", noticeDynamicUrl='" + noticeDynamicUrl + '\'' +
                ", noticeSystemUrl='" + noticeSystemUrl + '\'' +
                ", noticeUserUrl='" + noticeUserUrl + '\'' +
                ", circleFavoriteUrl='" + circleFavoriteUrl + '\'' +
                ", circleHotUrl='" + circleHotUrl + '\'' +
                ", circleBaseUrl='" + circleBaseUrl + '\'' +
                ", situationUrl='" + situationUrl + '\'' +
                ", faceContrast='" + faceContrast + '\'' +
                ", czrkHtmlUrl='" + czrkHtmlUrl + '\'' +
                ", wscapedConvictExamination='" + wscapedConvictExamination + '\'' +
                ", wzhcHtmlUrl='" + wzhcHtmlUrl + '\'' +
                ", clhcHtmlUrl='" + clhcHtmlUrl + '\'' +
                ", poisonHtmlUrl='" + poisonHtmlUrl + '\'' +
                ", wffzHtmlUrl='" + wffzHtmlUrl + '\'' +
                '}';
    }
}

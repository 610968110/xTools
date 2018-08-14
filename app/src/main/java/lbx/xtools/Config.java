package lbx.xtools;

import com.google.gson.annotations.SerializedName;

/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 * @date 2018/6/3.
 * GsonBean 登录获取app各种配置信息 包括gzb/sns的ip 账号密码等
 */

public class Config {

    @SerializedName("gzbIp")
    private String gzbIp;
    @SerializedName("opensnsAccessToken")
    private String snsToken;
    @SerializedName("gzbAppSecret")
    private String gzbAppSecret;
    @SerializedName("vidyoModelUrl")
    private String videoUrl;
    @SerializedName("opensnsIp")
    private String snsIp;
    @SerializedName("gzbAppKey")
    private String gzbAppKey;
    @SerializedName("gzbPort")
    private int gzbPort;
    @SerializedName("h5ServerUrl")
    private String h5ServerUrl;
    @SerializedName("user")
    private User user;

    public class User {
        @SerializedName("name")
        private String nickName;
        @SerializedName("phone")
        private String phone;
        @SerializedName("policeNumber")
        private String policeNumber;
        @SerializedName("idCard")
        private String idCard;
        @SerializedName("gzbPwd")
        private String gzbPwd;
        @SerializedName("gzbUserName")
        private String gzbUserName;
        @SerializedName("vidyoPwd")
        private String vidyoPwd;
        @SerializedName("opensnsUserName")
        private String opensnsUserName;
        @SerializedName("vidyoUserName")
        private String vidyoUserName;
        @SerializedName("opensnsPwd")
        private String opensnsPwd;
        @SerializedName("openId")
        private String openId;

        public String getNickName() {
            return nickName;
        }

        public String getPhone() {
            return phone;
        }

        public String getPoliceNumber() {
            return policeNumber;
        }

        public String getIdCard() {
            return idCard;
        }

        public String getGzbPwd() {
            return gzbPwd;
        }

        public String getGzbUserName() {
            return gzbUserName;
        }

        public String getVidyoPwd() {
            return vidyoPwd;
        }

        public String getOpensnsUserName() {
            return opensnsUserName;
        }

        public String getVidyoUserName() {
            return vidyoUserName;
        }

        public String getOpensnsPwd() {
            return opensnsPwd;
        }

        public String getOpenId() {
            return openId;
        }

        @Override
        public String toString() {
            return "User{" +
                    "nickName='" + nickName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", policeNumber='" + policeNumber + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", gzbPwd='" + gzbPwd + '\'' +
                    ", gzbUserName='" + gzbUserName + '\'' +
                    ", vidyoPwd='" + vidyoPwd + '\'' +
                    ", opensnsUserName='" + opensnsUserName + '\'' +
                    ", vidyoUserName='" + vidyoUserName + '\'' +
                    ", opensnsPwd='" + opensnsPwd + '\'' +
                    ", openId='" + openId + '\'' +
                    '}';
        }
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public User getUser() {
        return user;
    }

    public String getGzbAppKey() {
        return gzbAppKey;
    }

    public String getGzbAppSecret() {
        return gzbAppSecret;
    }

    public String getGzbIp() {
        return gzbIp;
    }

    public int getGzbPort() {
        return gzbPort;
    }

    public String getSnsIp() {
        return snsIp;
    }

    public String getSnsToken() {
        return snsToken;
    }

    public String getH5ServerUrl() {
        return h5ServerUrl;
    }

    @Override
    public String toString() {
        return "Config{" +
                "gzbIp='" + gzbIp + '\'' +
                ", snsToken='" + snsToken + '\'' +
                ", gzbAppSecret='" + gzbAppSecret + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", snsIp='" + snsIp + '\'' +
                ", gzbAppKey='" + gzbAppKey + '\'' +
                ", gzbPort='" + gzbPort + '\'' +
                ", h5ServerUrl='" + h5ServerUrl + '\'' +
                ", user=" + user +
                '}';
    }
}

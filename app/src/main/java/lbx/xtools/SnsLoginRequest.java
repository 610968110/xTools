package lbx.xtools;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
 * @date 2018/5/10.
 * GsonBean sns登录返回信息
 */

public class SnsLoginRequest implements Cloneable, Serializable {

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private Data data;
    @SerializedName("data_1")
    private Data1 data1;

    @Override
    public SnsLoginRequest clone() throws CloneNotSupportedException {
        super.clone();
        SnsLoginRequest snsLoginRequest = new SnsLoginRequest();
        snsLoginRequest.code = this.code;
        snsLoginRequest.data = this.data;
        snsLoginRequest.data1 = this.data1;
        return snsLoginRequest;
    }

    public int getCode() {
        return code;
    }

    public Data getData() {
        return data;
    }

    public Data1 getData1() {
        return data1;
    }


    public class Data implements Cloneable {
        @SerializedName("open_id")
        private String openId;
        @SerializedName("timestamp")
        private String timestamp;
        @SerializedName("auth")
        private Auth auth;

        public String getOpenId() {
            return openId;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public Auth getAuth() {
            return auth;
        }

        @Override
        protected Data clone() throws CloneNotSupportedException {
            super.clone();
            Data data = new Data();
            data.openId = this.openId;
            data.timestamp = this.timestamp;
            data.auth = this.auth;
            return data;
        }

        public class Auth implements Cloneable {
            @SerializedName("audit")
            private String audit;
            @SerializedName("last_login_time")
            private String lastLoginTime;
            @SerializedName("role_id")
            private String roleId;
            @SerializedName("uid")
            private String uid;
            @SerializedName("username")
            private String username;

            public String getAudit() {
                return audit;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public String getRoleId() {
                return roleId;
            }

            public String getUid() {
                return uid;
            }

            public String getUsername() {
                return username;
            }

            @Override
            protected Auth clone() throws CloneNotSupportedException {
                super.clone();
                Auth auth = new Auth();
                auth.lastLoginTime = this.lastLoginTime;
                auth.audit = this.audit;
                auth.roleId = this.roleId;
                auth.uid = this.uid;
                auth.username = this.username;
                return auth;
            }

            @Override
            public String toString() {
                return "Auth{" +
                        "audit='" + audit + '\'' +
                        ", lastLoginTime='" + lastLoginTime + '\'' +
                        ", roleId='" + roleId + '\'' +
                        ", uid='" + uid + '\'' +
                        ", username='" + username + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "SnsScoresUserBean{" +
                    "openId='" + openId + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", auth=" + auth +
                    '}';
        }
    }

    public class Data1 implements Cloneable {
        @SerializedName("avatar128")
        private String avatar128;
        @SerializedName("avatar512")
        private String avatar512;
        @SerializedName("con_check")
        private String conCheck;
        @SerializedName("email")
        private String email;
        @SerializedName("expand_info")
        private String[] expand_info;
        @SerializedName("fans")
        private String fans;
        @SerializedName("following")
        private String following;
        @SerializedName("is_admin")
        private boolean isAdmin;
        @SerializedName("is_followed")
        private boolean isFollowed;
        @SerializedName("is_following")
        private boolean isFollowing;
        @SerializedName("is_self")
        private int isSelf;
        @SerializedName("level")
        private Level level;
        @SerializedName("message_unread_count")
        private String messageUnreadCount;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("nickname")
        private String nickname;
        @SerializedName("now_shop_score")
        private String nowShopScore;
        @SerializedName("pos_city")
        private String posCity;
        @SerializedName("pos_district")
        private String posDistrict;
        @SerializedName("pos_province")
        private String posProvince;
        @SerializedName("rank_link")
        private String[] rankLink;
        @SerializedName("real_nickname")
        private String realNickname;
        @SerializedName("score")
        private String score;
        @SerializedName("score1")
        private String score1;
        @SerializedName("score2")
        private String score2;
        @SerializedName("score3")
        private String score3;
        @SerializedName("score4")
        private String score4;
        @SerializedName("sex")
        private String sex;
        @SerializedName("signature")
        private String signature;
        @SerializedName("tags")
        private String tags[];
        @SerializedName("title")
        private String title;
        @SerializedName("total_check")
        private String total_check;
        @SerializedName("uid")
        private String uid;
        @SerializedName("user_group")
        private String[] userGroup;
        @SerializedName("username")
        private String userName;
        @SerializedName("weibocount")
        private String weiboCount;

        public String getAvatar128() {
            return avatar128;
        }

        public String getAvatar512() {
            return avatar512;
        }

        public String getConCheck() {
            return conCheck;
        }

        public String getEmail() {
            return email;
        }

        public String[] getExpand_info() {
            return expand_info;
        }

        public String getFans() {
            return fans;
        }

        public String getFollowing() {
            return following;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public boolean isFollowed() {
            return isFollowed;
        }

        public boolean isFollowing() {
            return isFollowing;
        }

        public int getIsSelf() {
            return isSelf;
        }

        public Level getLevel() {
            return level;
        }

        public String getMessageUnreadCount() {
            return messageUnreadCount;
        }

        public String getMobile() {
            return mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public String getNowShopScore() {
            return nowShopScore;
        }

        public String getPosCity() {
            return posCity;
        }

        public String getPosDistrict() {
            return posDistrict;
        }

        public String getPosProvince() {
            return posProvince;
        }

        public String[] getRankLink() {
            return rankLink;
        }

        public String getRealNickname() {
            return realNickname;
        }

        public String getScore() {
            return score;
        }

        public String getScore1() {
            return score1;
        }

        public String getScore2() {
            return score2;
        }

        public String getScore3() {
            return score3;
        }

        public String getScore4() {
            return score4;
        }

        public String getSex() {
            return sex;
        }

        public String getSignature() {
            return signature;
        }

        public String[] getTags() {
            return tags;
        }

        public String getTitle() {
            return title;
        }

        public String getTotal_check() {
            return total_check;
        }

        public String getUid() {
            return uid;
        }

        public String[] getUserGroup() {
            return userGroup;
        }

        public String getUserName() {
            return userName;
        }

        public String getWeiboCount() {
            return weiboCount;
        }

        @Override
        protected Data1 clone() throws CloneNotSupportedException {
            super.clone();
            Data1 data1 = new Data1();
            data1.avatar128 = avatar128;
            data1.avatar512 = avatar512;
            data1.conCheck = conCheck;
            data1.email = email;
            data1.expand_info = expand_info;
            data1.fans = fans;
            data1.following = following;
            data1.isAdmin = isAdmin;
            data1.isFollowed = isFollowed;
            data1.isFollowing = isFollowing;
            data1.isSelf = isSelf;
            data1.level = level;
            data1.messageUnreadCount = messageUnreadCount;
            data1.mobile = mobile;
            data1.nickname = nickname;
            data1.nowShopScore = nowShopScore;
            data1.posCity = posCity;
            data1.posDistrict = posDistrict;
            data1.posProvince = posProvince;
            data1.rankLink = rankLink;
            data1.realNickname = realNickname;
            data1.score = score;
            data1.score1 = score1;
            data1.score2 = score2;
            data1.score3 = score3;
            data1.score4 = score4;
            data1.sex = sex;
            data1.signature = signature;
            data1.tags = tags;
            data1.title = title;
            data1.total_check = total_check;
            data1.uid = uid;
            data1.userGroup = userGroup;
            data1.userName = userName;
            data1.weiboCount = weiboCount;
            return data1;
        }

        public class Level implements Cloneable {
            @SerializedName("current")
            private String current;
            @SerializedName("left")
            private int left;
            @SerializedName("next")
            private String next;
            @SerializedName("percent")
            private String percent;
            @SerializedName("upgrade_require")
            private int upgradeRequire;

            public String getCurrent() {
                return current;
            }

            public String getSimpleCurrent() {
                String lv = "Lv";
                return !TextUtils.isEmpty(current) ? current.substring(current.indexOf(lv) + lv.length(), current.indexOf(" ")) : "";
            }

            public int getLeft() {
                return left;
            }

            public String getNext() {
                return next;
            }

            public String getPercent() {
                return percent;
            }

            public int getUpgradeRequire() {
                return upgradeRequire;
            }

            @Override
            protected Level clone() throws CloneNotSupportedException {
                super.clone();
                Level level = new Level();
                level.current = this.current;
                level.left = this.left;
                level.next = this.next;
                level.percent = this.percent;
                level.upgradeRequire = this.upgradeRequire;
                return level;
            }

            @Override
            public String toString() {
                return "Level{" +
                        "current='" + current + '\'' +
                        ", left=" + left +
                        ", next='" + next + '\'' +
                        ", percent='" + percent + '\'' +
                        ", upgradeRequire=" + upgradeRequire +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data1{" +
                    "avatar128='" + avatar128 + '\'' +
                    ", avatar512='" + avatar512 + '\'' +
                    ", conCheck='" + conCheck + '\'' +
                    ", email='" + email + '\'' +
                    ", expand_info=" + expand_info +
                    ", fans='" + fans + '\'' +
                    ", following='" + following + '\'' +
                    ", isAdmin=" + isAdmin +
                    ", isFollowed=" + isFollowed +
                    ", isFollowing=" + isFollowing +
                    ", isSelf=" + isSelf +
                    ", level=" + level +
                    ", messageUnreadCount='" + messageUnreadCount + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", nowShopScore='" + nowShopScore + '\'' +
                    ", posCity='" + posCity + '\'' +
                    ", posDistrict='" + posDistrict + '\'' +
                    ", posProvince='" + posProvince + '\'' +
                    ", rankLink=" + rankLink +
                    ", realNickname='" + realNickname + '\'' +
                    ", score='" + score + '\'' +
                    ", score1='" + score1 + '\'' +
                    ", score2='" + score2 + '\'' +
                    ", score3='" + score3 + '\'' +
                    ", score4='" + score4 + '\'' +
                    ", sex='" + sex + '\'' +
                    ", signature='" + signature + '\'' +
                    ", tags='" + tags + '\'' +
                    ", title='" + title + '\'' +
                    ", total_check='" + total_check + '\'' +
                    ", uid='" + uid + '\'' +
                    ", userGroup=" + userGroup +
                    ", userName='" + userName + '\'' +
                    ", weiboCount='" + weiboCount + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SnsLoginRequest{" +
                "code=" + code +
                ", data=" + data +
                ", data1=" + data1 +
                '}';
    }
}


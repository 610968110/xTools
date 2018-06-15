package lbx.xtools;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author lbx
 * @date 2018/5/11.
 * GsonBean 积分累签榜
 */

public class ScoresBean implements Serializable {
    private int code;
    private String info;
    @SerializedName("data")
    private List<UserBean> users;

    public class UserBean {
        private String id;
        private String uid;
        @SerializedName("create_time")
        private String createTime;
        @SerializedName("total_check")
        private String totalCheck;
        private User user;

        public class User {
            private String uid;
            private String title;
            private String nickname;
            private String[] rank_link;
            private String score1;
            private String signature;
            private String real_nickname;
            private String avatar128;
            private String avatar512;

            public String getUid() {
                return uid;
            }

            public String getTitle() {
                return title;
            }

            public String getNickname() {
                return nickname;
            }

            public String[] getRank_link() {
                return rank_link;
            }

            public String getScore1() {
                return score1;
            }

            public String getSignature() {
                return signature;
            }

            public String getReal_nickname() {
                return real_nickname;
            }

            public String getAvatar128() {
                return avatar128;
            }

            public String getAvatar512() {
                return avatar512;
            }

            @Override
            public String toString() {
                return "User{" +
                        "uid='" + uid + '\'' +
                        ", title='" + title + '\'' +
                        ", nickname='" + nickname + '\'' +
                        ", rank_link=" + Arrays.toString(rank_link) +
                        ", score1='" + score1 + '\'' +
                        ", signature='" + signature + '\'' +
                        ", real_nickname='" + real_nickname + '\'' +
                        ", avatar128='" + avatar128 + '\'' +
                        ", avatar512='" + avatar512 + '\'' +
                        '}';
            }
        }

        public String getId() {
            return id;
        }

        public String getUid() {
            return uid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getTotalCheck() {
            return totalCheck;
        }

        public String getTotalCheckString() {
            return totalCheck + "天";
        }

        public User getUser() {
            return user;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "id='" + id + '\'' +
                    ", uid='" + uid + '\'' +
                    ", totalCheck='" + totalCheck + '\'' +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public List<UserBean> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "ScoresBean{" +
                "code=" + code +
                ", info='" + info + '\'' +
                ", users=" + users.toString() +
                '}';
    }
}

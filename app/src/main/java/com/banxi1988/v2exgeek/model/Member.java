package com.banxi1988.v2exgeek.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by banxi on 15/5/31.
 */
public class Member implements Parcelable {
    public long id;
    public String username;
    public String tabline;
    public String avatar_mini;
    public String avatar_normal;
    public String avatar_large;

    // V2EX返回的没有带协议的如  "//cdn.v2ex.co/gravatar/8c628c1eac045670ad13b7b1d59924e3?s=48&d=retro"
    // s 表示大小 large的是 73
    public String getAvatar(){
       return "http:"+avatar_large;
    }


    // Parcle

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.username);
        dest.writeString(this.tabline);
        dest.writeString(this.avatar_mini);
        dest.writeString(this.avatar_normal);
        dest.writeString(this.avatar_large);
    }

    public Member() {
    }

    protected Member(Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
        this.tabline = in.readString();
        this.avatar_mini = in.readString();
        this.avatar_normal = in.readString();
        this.avatar_large = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        public Member createFromParcel(Parcel source) {
            return new Member(source);
        }

        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
}

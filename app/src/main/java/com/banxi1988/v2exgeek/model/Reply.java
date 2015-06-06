package com.banxi1988.v2exgeek.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by banxi on 15/5/31.
 */
public class Reply implements Parcelable {
    public long id;
    public int thanks;
    public String content;
    public String content_rendered;
    public  Member member;
        // "created" : 1433034989,
    public long created;
    public long last_modified;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.thanks);
        dest.writeString(this.content);
        dest.writeString(this.content_rendered);
        dest.writeParcelable(this.member, 0);
        dest.writeLong(this.created);
        dest.writeLong(this.last_modified);
    }

    public Reply() {
    }

    protected Reply(Parcel in) {
        this.id = in.readLong();
        this.thanks = in.readInt();
        this.content = in.readString();
        this.content_rendered = in.readString();
        this.member = in.readParcelable(Member.class.getClassLoader());
        this.created = in.readLong();
        this.last_modified = in.readLong();
    }

    public static final Parcelable.Creator<Reply> CREATOR = new Parcelable.Creator<Reply>() {
        public Reply createFromParcel(Parcel source) {
            return new Reply(source);
        }

        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };
}

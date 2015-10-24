package com.banxi1988.v2exgeek.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

/**
 * Created by banxi on 15/5/31.
 */
public class Topic implements Parcelable {
    public long id;
    public String title;
    public String url;
    public String content;
    public String content_rendered;
    public int replies;
    public Member member;
    public Node node;

    // "created" : 1433034989,
    public long created;
    public long last_modified;
    public long last_touched;

    //
    public CharSequence getFriendlyTime(){
        return DateUtils.getRelativeTimeSpanString(created * 1000);
    }

    public CharSequence getLastModifiedTime(){
        return DateUtils.getRelativeTimeSpanString(last_modified * 1000);
    }

    public CharSequence getLastTouchedTime(){
        return DateUtils.getRelativeTimeSpanString(last_touched * 1000);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "title='" + title + '\'' +
                '}';
    }


    // Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.content);
        dest.writeString(this.content_rendered);
        dest.writeInt(this.replies);
        dest.writeParcelable(this.member, 0);
        dest.writeParcelable(this.node, 0);
        dest.writeLong(this.created);
        dest.writeLong(this.last_modified);
        dest.writeLong(this.last_touched);
    }

    public Topic() {
    }

    protected Topic(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.url = in.readString();
        this.content = in.readString();
        this.content_rendered = in.readString();
        this.replies = in.readInt();
        this.member = in.readParcelable(Member.class.getClassLoader());
        this.node = in.readParcelable(Node.class.getClassLoader());
        this.created = in.readLong();
        this.last_modified = in.readLong();
        this.last_touched = in.readLong();
    }

    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}

package com.banxi1988.v2exgeek.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by banxi on 15/5/31.
 */
public class Node implements Parcelable {
    public long id;
    public String name;
    public String title;
    public String title_alternative;
    public String url;
    public int topics;
    public String avatar_mini;
    public String avatar_normal;
    public String avatar_large;

    public String getAvatar(){
        return "http:"+avatar_large;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
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
        dest.writeString(name);
        dest.writeString(this.title);
        dest.writeString(this.title_alternative);
        dest.writeString(this.url);
        dest.writeInt(this.topics);
        dest.writeString(this.avatar_mini);
        dest.writeString(this.avatar_normal);
        dest.writeString(this.avatar_large);
    }

    public Node() {
    }

    protected Node(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.title = in.readString();
        this.title_alternative = in.readString();
        this.url = in.readString();
        this.topics = in.readInt();
        this.avatar_mini = in.readString();
        this.avatar_normal = in.readString();
        this.avatar_large = in.readString();
    }

    public static final Parcelable.Creator<Node> CREATOR = new Parcelable.Creator<Node>() {
        public Node createFromParcel(Parcel source) {
            return new Node(source);
        }

        public Node[] newArray(int size) {
            return new Node[size];
        }
    };

    //preselectedNode
    public Node(long id,String name,String title,String avatar_large){
        this.id = id;
        this.name = name;
        this.title = title;
        this.avatar_large = avatar_large;
    }
}

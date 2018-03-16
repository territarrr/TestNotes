package s.s.testnotes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sovochka on 16.03.2018.
 */

public class Note implements Parcelable{
   private long _id;
   private String text;
   public Note(long _id, String text) {
       this._id = _id;
       this.text = text;
   }

    protected Note(Parcel in) {
        _id = in.readLong();
        text = in.readString();
    }

    public void setId(long _id) {
       this._id = _id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId()
    {
        return _id;
    }

    public String getText() {
       return text;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(text);
    }
}

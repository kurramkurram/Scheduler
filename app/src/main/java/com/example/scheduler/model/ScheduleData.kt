package com.example.scheduler.model

import android.os.Parcel
import android.os.Parcelable

data class ScheduleData(
    var id: Long = 0,
    var date: String?,
    var title: String?,
    var content: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readLong()
        date = parcel.readString()
        title = parcel.readString()
        content = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(date)
        dest.writeString(title)
        dest.writeString(content)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ScheduleData> {
        override fun createFromParcel(parcel: Parcel): ScheduleData {
            return ScheduleData(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleData?> {
            return arrayOfNulls(size)
        }
    }
}
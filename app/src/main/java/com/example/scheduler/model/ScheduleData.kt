package com.example.scheduler.model

import android.os.Parcel
import android.os.Parcelable

class ScheduleData : Parcelable {

    var id: Long = 0
    var date: String? = null
    var title: String? = null
    var content: String? = null
    var category: String? = null

    constructor(id: Long, date: String?, title: String?, content: String?, category: String?) {
        this.id = id
        this.date = date
        this.title = title
        this.content = content
        this.category = category
    }

    constructor(parcel: Parcel) {
        id = parcel.readLong()
        date = parcel.readString()
        title = parcel.readString()
        content = parcel.readString()
        category = parcel.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(date)
        dest.writeString(title)
        dest.writeString(content)
        dest.writeString(category)
    }

    override fun describeContents() = 0

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<ScheduleData> = object : Parcelable.Creator<ScheduleData> {
            override fun createFromParcel(source: Parcel): ScheduleData = ScheduleData(source)

            override fun newArray(size: Int): Array<ScheduleData?> = arrayOfNulls(size)

        }
    }
}
package com.ganpatibaapaa.motivational.status.video.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Video(
    @SerializedName("id")
    var ID: Int,

    @SerializedName("url")
    var URL: String,

    @SerializedName("thumb_url")
    var ThumbURL: String
): Serializable

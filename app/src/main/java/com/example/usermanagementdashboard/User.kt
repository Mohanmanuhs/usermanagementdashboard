package com.example.usermanagementdashboard

class User {
    var little:String? = null
    var large:String? = null
    var img:String? = null
    constructor()
    constructor(img:String,large:String,little:String){
        this.img=img
        this.little=little
        this.large=large
    }
}
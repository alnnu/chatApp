package com.example.mychatapp.models

class Message {
    var text: String? = null
    var idUser: String? = null

    constructor() {}

    constructor(text: String?, idUser: String?) {
        this.text = text
        this.idUser = idUser
    }
}
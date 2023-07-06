package com.example.mychatapp.models

class User {
    var nome: String? = null
    var email: String? = null
    var uid: String? = null

    constructor(){}

    constructor(nome:String?, email:String?, uid:String?) {
        this.nome = nome
        this.email = email
        this.uid = uid
    }

}
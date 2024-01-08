package com.example.cookpal.Administrator

class ModeloReceta {
    var uid:String= ""
    var id: String= ""
    var titulo:String= ""
    var ingredientes= ""
    var categoria= ""
    var url:String= ""
    var tiempo:Long=0
// contructor vacio para lectura de firebase
    constructor()

    constructor(
        uid: String,
        id: String,
        titulo: String,
        ingredientes: String,
        categoria: String,
        url: String,
        tiempo: Long
    ) {
        this.uid = uid
        this.id = id
        this.titulo = titulo
        this.ingredientes = ingredientes
        this.categoria = categoria
        this.url = url
        this.tiempo = tiempo
    }


}
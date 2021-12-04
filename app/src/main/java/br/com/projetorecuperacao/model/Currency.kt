package br.com.projetorecuperacao.model

class Currency {

    data class Data(
        var code: String,
        var codein: String,
        var name: String,
        var high: String,
        var low: String,
        var varBid: String,
        var pctChange: String,
        var bid: String,
        var ask: String,
        var timestamp: String,
        var create_date: String,
        var USDBRL: USDBRL = USDBRL(
            code,
            codein,
            name,
            high,
            low,
            varBid,
            pctChange,
            bid,
            ask,
            timestamp,
            create_date
        )
    )
}
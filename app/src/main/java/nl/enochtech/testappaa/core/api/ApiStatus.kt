package nl.enochtech.testappaa.core.api

import com.google.gson.JsonObject

class ApiStatus {

    internal lateinit var jsonObject: JsonObject
    internal lateinit var data: Any
    internal lateinit var dataList: List<Any>
    internal var message: String? = null
    private var code: Int = 0
    internal var statusEnum: StatusEnum

    constructor(message: String, statusEnum: StatusEnum, data: Any) {
        this.message = message
        this.data = data
        this.statusEnum = statusEnum
    }

    constructor(statusEnum: StatusEnum, data: Any) {
        this.data = data
        this.statusEnum = statusEnum
    }

    constructor(statusEnum: StatusEnum) {
        this.statusEnum = statusEnum
    }

}
package net.spring.cloud.prototype.dataaccess.uuid

import java.util.*

class UuidMapper {
    companion object{
        fun fromUUIDString(uuidString: String): UUID = UUID.fromString(uuidString)
    }
}
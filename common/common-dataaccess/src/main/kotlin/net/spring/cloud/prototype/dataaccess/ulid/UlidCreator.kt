package net.spring.cloud.prototype.dataaccess.ulid

import com.github.f4b6a3.ulid.UlidCreator
import java.util.*

class UlidCreator {
    companion object{
        fun monotonicUuid(): UUID = UlidCreator.getMonotonicUlid().toUuid()
    }
}
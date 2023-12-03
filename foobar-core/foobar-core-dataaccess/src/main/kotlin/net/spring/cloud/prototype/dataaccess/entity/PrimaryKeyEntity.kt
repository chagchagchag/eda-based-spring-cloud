package net.spring.cloud.prototype.dataaccess.entity

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*

@MappedSuperclass
abstract class PrimaryKeyEntity : Persistable<UUID> {
    @Id
    @Column(columnDefinition = "uuid")
    private val id: UUID = UlidCreator.getMonotonicUlid().toUuid()

    @Transient
    private var _isNew = true


    ///// Object equals, hashCode
    override fun equals(other: Any?): Boolean {
        if(other == null)
            return false

        if(other !is HibernateProxy && this::class != other::class)
            return false

        return id == getIdentifier(other)
    }

    override fun hashCode() = Objects.hashCode(id)

    ///// Persistable getId(), isNew()
    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    @PostPersist
    @PostLoad
    protected fun load(){
        _isNew = false
    }

    private fun getIdentifier(obj: Any): Serializable {
        return if (obj is HibernateProxy) ({
            obj.hibernateLazyInitializer.identifier
        }) as Serializable
        else{
            (obj as PrimaryKeyEntity).id
        }
    }
}
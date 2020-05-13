package ru.gimadiew.voting.model;

import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity implements Persistable<Integer> {

    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    public AbstractBaseEntity() {
    }

    public AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    // doesn't work for hibernate lazy proxy
    public int id() {
        Assert.notNull(id, "Entity must has id");
        return id;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //https://thoughts-on-java.org/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/
    @Override
    public int hashCode() {
        return 13;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractBaseEntity other = (AbstractBaseEntity) obj;
        return id != null && id.equals(other.getId());
    }
}

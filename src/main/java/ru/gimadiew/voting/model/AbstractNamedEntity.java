package ru.gimadiew.voting.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    public AbstractNamedEntity() {
    }

    public AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }
}

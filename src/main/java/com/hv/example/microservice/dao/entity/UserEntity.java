package com.hv.example.microservice.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "username")
    @EqualsAndHashCode.Include
    private String username;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "password")
    @ToString.Exclude
    private String password;

    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "email",unique = true)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "created_at",updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /** constructor with args **/

    public UserEntity(@NotNull @Size(min = 1, max = 100) String username) {
        this.username = username;
    }
}

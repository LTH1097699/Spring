package com.springbook.entity.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import com.springbook.entity.AbstractEntity;

@Entity
@Table(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorEntity extends AbstractEntity {
    @NaturalId(mutable = true)
    @Column(name = "codeauthor", nullable = false, unique = true)
    private String codeAuthor;

    @Column(name = "nameAuthor", nullable = false)
    private String nameAuthor;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Builder.Default
    private List<BookEntity> books = new ArrayList<>();
}

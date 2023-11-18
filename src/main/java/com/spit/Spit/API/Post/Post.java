package com.spit.Spit.API.Post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spit.Spit.API.Account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NamedNativeQuery(
        name = "getAllPostsDesc-query",
        query = """
                SELECT a.handle, a.name, p.date, p.message, p.id FROM account a
                JOIN post p ON a.account_id = p.account_id
                ORDER BY p.date DESC
                """,
        resultSetMapping = "mapToGetPostDTO"
)

@NamedNativeQuery(
        name = "getPostsByHandleDesc-query",
        query = """
                SELECT a.handle, a.name, p.date, p.message, p.id FROM account a
                JOIN post p ON a.account_id = p.account_id
                WHERE a.handle = :handle
                ORDER BY p.date DESC;
                """,
        resultSetMapping = "mapToGetPostDTO"
)

@SqlResultSetMapping(
        name = "mapToGetPostDTO",
        classes =   @ConstructorResult( targetClass = GetPostDTO.class,
                                        columns = {
                                            @ColumnResult(name = "id", type = Long.class),
                                            @ColumnResult(name = "name", type = String.class),
                                            @ColumnResult(name = "handle", type = String.class),
                                            @ColumnResult(name = "date", type = Date.class),
                                            @ColumnResult(name = "message", type = String.class)
                                         }))

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Date date = new Date();

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @Transient
    private String accountHandle;

    public Post(Date date, String message, Account account) {
        this.date = date;
        this.message = message;
        this.account = account;
    }
    public String getAccountHandle() {
        if (account != null) {
            accountHandle = account.getHandle();
        }
        return accountHandle;
    }
}

package joelmaciel.service_control.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class Client {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    private OffsetDateTime creationDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @UpdateTimestamp
    private OffsetDateTime updateDate;



}

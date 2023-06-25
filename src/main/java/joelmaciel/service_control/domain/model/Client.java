package joelmaciel.service_control.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Column(nullable = false, length = 150)
    @NotBlank
    private String name;

    @Column(nullable = false, length = 11)
    @CPF
    @NotNull
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreationTimestamp
    private OffsetDateTime creationDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @UpdateTimestamp
    private OffsetDateTime updateDate;



}

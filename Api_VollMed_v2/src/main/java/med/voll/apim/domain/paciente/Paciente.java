package med.voll.apim.domain.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.apim.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity(name = "pacientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    @Embedded
    private Endereco endereco;


}

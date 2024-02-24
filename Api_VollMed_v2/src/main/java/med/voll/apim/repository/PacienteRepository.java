package med.voll.apim.repository;

import med.voll.apim.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, String> {
    void deleteById(String id);
}

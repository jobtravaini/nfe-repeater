package com.job.nfe_repeater.repository;

import com.job.nfe_repeater.error.NfeNotFoundException;
import com.job.nfe_repeater.nfe.Nfe;
import com.job.nfe_repeater.nfe.NfeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NfeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NfeRepository repository;

    @Test
    public void whenFindById_thenReturnNfe() {
        Nfe nfe = insertNfeEntry("1", "<NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"></NFe>");
        Nfe found = repository.findById("1").orElseThrow(() -> new NfeNotFoundException("1"));

        Assertions.assertThat(nfe.getXml())
                .isEqualTo(found.getXml());
    }

    @Test
    public void whenNotFindById_thenExceptionIsThrown() {
        String key = "2";
        Assertions.assertThatThrownBy(() -> repository.findById(key).orElseThrow(() -> new NfeNotFoundException(key)))
                .isInstanceOf(NfeNotFoundException.class)
                .hasMessageContaining("2");
    }

    @Test
    public void whenInsertingSameId_thenExceptionIsThrown() {
        insertNfeEntry("1", "<NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"></NFe>");
        Assertions.assertThatThrownBy(() -> insertNfeEntry("1", "<NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"></NFe>"))
                .isInstanceOf(EntityExistsException.class);
    }

    private Nfe insertNfeEntry(String accessKey, String xml) {
        Nfe nfe = new Nfe(accessKey, xml);
        entityManager.persist(nfe);
        entityManager.flush();

        return nfe;
    }
}

package com.rest.silver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.silver.dao.Silver;
import com.rest.silver.dao.SilverRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SilverRepositoryTest {
	
	@Autowired
    private TestEntityManager entityManager;
 
	@Autowired
    private SilverRepository silverRepositoryMock;
    
	@Test
    public void whenFindByUserId_thenReturnSilver() {
        // given
        Silver sil = new Silver("123345","");
        entityManager.persist(sil);
        entityManager.flush();
     
        // when
        Silver found = silverRepositoryMock.findOne(sil.getUserId());
     
        // then
        assertThat(sil.getUserId()).isEqualTo(found.getUserId());
    }

}

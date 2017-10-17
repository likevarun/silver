package com.rest.silver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rest.silver.dao.Silver;
import com.rest.silver.dao.SilverRepository;

/**
 * Unit test for SilverService
 */
@RunWith(SpringRunner.class)
public class SilverServiceImplTest {

	@MockBean
    private SilverRepository silverRepositoryMock;

	@Before
	public void setUp() {
	    Silver sil = new Silver("123455","");
	    Mockito.when(silverRepositoryMock.findOne(sil.getUserId()))
	      .thenReturn(sil);
	}

	@Test
	public void whenValidUserId_thenSilverShouldBeFound() {
	    String userId = "123455";
	    Silver found = silverRepositoryMock.findOne(userId);
	     assertThat(found.getUserId())
	      .isEqualTo(userId);
	 }

	@Test
	public void testGetState(){
		String userId = "123455";
		String state = "";
	    String found = silverRepositoryMock.findOne(userId).getState();
	     assertThat(found)
	      .isEqualTo(state);
	}
}

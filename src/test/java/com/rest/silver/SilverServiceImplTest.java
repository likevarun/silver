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
import com.rest.silver.dao.SilverService;

/**
 * Unit test for SilverService
 */
@RunWith(SpringRunner.class)
public class SilverServiceImplTest {
	
	@MockBean
    private SilverService silverServiceMock;

	@MockBean
    private SilverRepository silverRepositoryMock;

	@Before
	public void setUp() {
	    Silver sil = new Silver("123455","aabbcc11dd22ee");
	    Mockito.when(silverRepositoryMock.findOne(sil.getUserId()))
	      .thenReturn(sil);
	    Mockito.when(silverServiceMock.getSum(sil.getUserId()))
	      .thenReturn(33);
	    Mockito.when(silverServiceMock.getChars(sil.getUserId()))
	      .thenReturn("aabbccddee");
	}

	@Test
	public void whenValidUserId_thenSilverShouldBeFound() {
	    String userId = "123455";
	    Silver found = silverRepositoryMock.findOne(userId);
	     assertThat(found.getUserId())
	      .isEqualTo(userId);
	 }

	@Test
	public void whenValidUserId_thenSilverShouldValidateState(){
		String userId = "123455";
		String state = "aabbcc11dd22ee";
	    String found = silverRepositoryMock.findOne(userId).getState();
	     assertThat(found)
	      .isEqualTo(state);
	}

	@Test
	public void whenGetSum_thenSilverShouldReturnSumInt(){
		String userId = "123455";
		int sum = 33;
	    int found = silverServiceMock.getSum(userId);
	     assertThat(found)
	      .isEqualTo(sum);
	}

	@Test
	public void whenGetChars_thenSilverShouldReturnState(){
		String userId = "123455";
		String state = "aabbccddee";
	    String found = silverServiceMock.getChars(userId);
	     assertThat(found)
	      .isEqualTo(state);
	}
}

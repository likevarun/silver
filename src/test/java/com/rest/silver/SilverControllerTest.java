package com.rest.silver;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rest.silver.controller.SilverRestController;
import com.rest.silver.dao.Silver;
import com.rest.silver.dao.SilverService;

@RunWith(SpringRunner.class)
@WebMvcTest(SilverRestController.class)
public class SilverControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SilverService silverService;

	@Test
	public void testGetState() throws Exception {
		Silver sil = new Silver("12345", "aabbccdd");
		Mockito.when(silverService.checkUser("12345")).thenReturn(sil);
		this.mvc.perform(get("/state?userId=12345")).andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()));
	}

	@Test
	public void testGetChars() throws Exception {
		Silver sil = new Silver("12345", "aabbccdd");
		Mockito.when(silverService.checkUser("12345")).thenReturn(sil);
		Mockito.when(silverService.getChars("12345")).thenReturn("aabbccdd");
		mvc.perform(get("/chars?userId=12345")).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	}

	@Test
	public void testGetSum() throws Exception {
		Silver sil = new Silver("12345", "aabb11cc22dd");
		Mockito.when(silverService.checkUser("12345")).thenReturn(sil);
		Mockito.when(silverService.getSum("12345")).thenReturn(33);
		mvc.perform(get("/sum?userId=12345")).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	}

	@Test
	public void testDeleteChars() throws Exception {
		Silver sil = new Silver("12345", "aabbccdd");
		Mockito.when(silverService.checkUser("12345")).thenReturn(sil);
		Mockito.when(silverService.deleteChars("12345", 'd')).thenReturn(true);
		Mockito.when(silverService.getState("12345")).thenReturn("aabbccd");
		mvc.perform(delete("/chars/d?userId=12345")).andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()));
	}
}

package br.edu.iftm.extensaoSTS.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.iftm.extensaoSTS.domain.Atividade;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtividadeResourceTest {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;
	private String uri = "/atividades/";

	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void testaId() throws Exception {
		this.mvc.perform(get(uri + "1")).andExpect(status().isOk())
				.andExpect(jsonPath("nome", is("Visita Técnica GDG 2018")));
	}

	@Test
	public void testaNotFound() throws Exception {
		this.mvc.perform(get(uri + "10")).andExpect(status().isNotFound());
	}

	@Test
	public void bucarTodos() throws Exception {
		this.mvc.perform(get(uri)).andExpect(status().isOk());
	}

	@Test
	public void salvar() throws Exception {
		Atividade atividade = new Atividade("Teste");
		atividade.setId(0);
		String jsonContent = new ObjectMapper().writeValueAsString(atividade);

		this.mvc.perform(post(uri, atividade)
				.content(jsonContent)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void excluir() throws Exception {
		this.mvc.perform(delete(uri + "2")).andExpect(status().isNoContent());
	}
}

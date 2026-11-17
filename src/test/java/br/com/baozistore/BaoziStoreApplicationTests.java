package br.com.baozistore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:baoziteste;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class BaoziStoreApplicationTests {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @Test
    void deveCadastrarConsultarListarEExcluirAsTresEntidades() throws Exception {
        MvcResult clienteCriado = mvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Cliente Teste\",\"clienteDesde\":\"2026-11-17\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nome").value("Cliente Teste"))
            .andReturn();
        Long clienteId = idDoResultado(clienteCriado);
        mvc.perform(get("/clientes")).andExpect(status().isOk());
        mvc.perform(get("/clientes/{id}", clienteId)).andExpect(status().isOk());

        MvcResult produtoCriado = mvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Baozi tradicional\",\"preco\":8.50,\"estoque\":true}"))
            .andExpect(status().isCreated())
            .andReturn();
        Long produtoId = idDoResultado(produtoCriado);
        mvc.perform(get("/produtos")).andExpect(status().isOk());
        mvc.perform(get("/produtos/{id}", produtoId)).andExpect(status().isOk());

        MvcResult pedidoCriado = mvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"clienteId\":" + clienteId + ",\"produtoId\":" + produtoId + ",\"quantidade\":3}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.quantidade").value(3))
            .andReturn();
        Long pedidoId = idDoResultado(pedidoCriado);
        mvc.perform(get("/pedidos")).andExpect(status().isOk());
        mvc.perform(get("/pedidos/{id}", pedidoId)).andExpect(status().isOk());

        mvc.perform(delete("/pedidos/{id}", pedidoId)).andExpect(status().isNoContent());
        mvc.perform(delete("/clientes/{id}", clienteId)).andExpect(status().isNoContent());
        mvc.perform(delete("/produtos/{id}", produtoId)).andExpect(status().isNoContent());
    }

    private Long idDoResultado(MvcResult resultado) throws Exception {
        JsonNode json = mapper.readTree(resultado.getResponse().getContentAsString());
        return json.get("id").asLong();
    }
}

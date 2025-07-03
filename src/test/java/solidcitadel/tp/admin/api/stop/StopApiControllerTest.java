package solidcitadel.tp.admin.api.stop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class StopApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private RestDocumentationResultHandler restDocs;

    @BeforeEach
    void setUp() {
        restDocs = document("{method-name}");
    }

    @Test
    void getStops() throws Exception {
        mockMvc.perform(get("/api/stops"))
                .andExpect(status().isOk())
                .andDo(restDocs);
    }

    @Test
    void createStop() throws Exception {
        String stopJson = """
            {
                "name": "테스트정류장",
                "transportType": "BUS"
            }
            """;
        String response = mockMvc.perform(post("/api/stops")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stopJson))
                .andExpect(status().isCreated())
                .andDo(restDocs)
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = Long.parseLong(response.trim());
        assertNotNull(id);
    }

    @Test
    void getStopDetail() throws Exception {
        String response = mockMvc.perform(post("/api/stops")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"상세정류장","transportType":"BUS"}
                """))
                .andDo(restDocs)
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long stopId = Long.parseLong(response.trim());

        mockMvc.perform(get("/api/stops/{id}", stopId))
                .andExpect(status().isOk())
                .andDo(restDocs);
    }

    @Test
    void updateStop() throws Exception {
        String response = mockMvc.perform(post("/api/stops")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"수정대상정류장","transportType":"BUS"}
                """))
                .andDo(restDocs)
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long stopId = Long.parseLong(response.trim());

        String updateJson = """
            {
                "name": "수정된정류장",
                "transportType": "SUBWAY"
            }
            """;
        mockMvc.perform(put("/api/stops/{id}", stopId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isNoContent())
                .andDo(restDocs);
    }

    @Test
    void deleteStop() throws Exception {
        String response = mockMvc.perform(post("/api/stops")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"삭제대상정류장","transportType":"BUS"}
                """))
                .andDo(restDocs)
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long stopId = Long.parseLong(response.trim());

        mockMvc.perform(delete("/api/stops/{id}", stopId))
                .andExpect(status().isNoContent())
                .andDo(restDocs);
    }
}
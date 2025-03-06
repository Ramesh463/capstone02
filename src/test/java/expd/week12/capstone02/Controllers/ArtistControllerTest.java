package expd.week12.capstone02.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import expd.week12.capstone02.Domain.Artist;
import expd.week12.capstone02.Domain.Genre;
import expd.week12.capstone02.services.ArtistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static javax.swing.UIManager.put;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Tag("integration")
class ArtistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetAllArtists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Artists"))
                .andExpect(status().isOk());
                //.andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetArtistById() throws Exception {
        Artist artist = Artist.builder()
                .id(1L)
                .artistName("Test")
                .genre(Genre.valueOf("ROCK"))
                .country("Country")
                .build();

        artist = artistService.createArtist(artist);
        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Artists/" + artist.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(artist.getId()));
    }

    @Test
    public void testSaveArtist() throws Exception {
        Artist artist = Artist.builder()
                .id(1L)
                .artistName("Test")
                .genre(Genre.ROCK)
                .country("Country")
                .build();
        //Artist savedartist = artistService.createArtist(artist);

         mockMvc.perform(MockMvcRequestBuilders.post("/Admin/Artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artistName").value(artist.getArtistName()));

    }

    @Test
    public void testUpdateArtist() throws Exception {
        Artist artist = Artist.builder()
                .id(1L)
                .artistName("Test")
                .genre(Genre.POP)
                .country("Country")
                .build();
        artist = artistService.createArtist(artist);
        artist.setArtistName("Updated Test");

        mockMvc.perform(MockMvcRequestBuilders.put("/Admin/Artists/" + artist.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(artist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artistName").value("Updated Test"));
    }

    @Test
    public void testDeleteArtist() throws Exception {
        Artist artist = Artist.builder()
                .id(1L)
                .artistName("Test")
                .genre(Genre.valueOf("ROCK"))
                .country("Country")
                .build();

        artist = artistService.createArtist(artist);

        mockMvc.perform(MockMvcRequestBuilders.delete("/Admin/Artists/" + artist.getId()))
                .andExpect(status().isAccepted());
        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Artists/" + artist.getId()))
                .andExpect(status().isOk());
    }


}
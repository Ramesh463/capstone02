package expd.week12.capstone02.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import expd.week12.capstone02.Domain.Genre;
import expd.week12.capstone02.Domain.Track;
import expd.week12.capstone02.services.TrackService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Tag("integration")
class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrackService trackService;

    @Test
    public void testGetAllTracks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Track"))
                .andExpect(status().isOk());
                //.andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetTrackById() throws Exception {
        Track track = Track.builder()
                .id(1L)
                .trackName("Track Name")
                .movieName("Movie Name")
                .genre(Genre.POP)
                .build();

        track = trackService.createTrack(track);

        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Track/" + track.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(track.getId()));
    }

    @Test
    public void testCreateTrack() throws Exception {
        Track  track = Track.builder()
                .trackName("Track Name")
                .genre(Genre.POP)
                .movieName("Movie Name")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/Admin/Track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(track)))
                        .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackName").value(track.getTrackName()));
    }

    @Test
    public void testUpdateTrack() throws Exception {
        Track track = Track.builder()
                .id(1L)
                .trackName("Track Name")
                .genre(Genre.ROCK)
                .movieName("Movie Name")
                .build();
        track = trackService.createTrack(track);
        track.setTrackName("New Track Name");

        mockMvc.perform(MockMvcRequestBuilders.put("/Admin/Track/" + track.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackName").value(track.getTrackName()));
    }

    @Test
    public void testDeleteTrack() throws Exception {
        Track   track = Track.builder()
                .id(1L)
                .trackName("Track Name")
                .genre(Genre.POP)
                .movieName("Movie Name")
                .build();
        track = trackService.createTrack(track);
        mockMvc.perform(MockMvcRequestBuilders.delete("/Admin/Track/" + track.getId()))
                .andExpect(status().isAccepted());
        mockMvc.perform(MockMvcRequestBuilders.get("/Admin/Track/" + track.getId()))
                .andExpect(status().isOk());
    }
}
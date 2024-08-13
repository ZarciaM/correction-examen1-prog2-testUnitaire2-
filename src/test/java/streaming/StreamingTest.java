package streaming;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamingTest {

    private Genre rock, pop, jazz;
    private SoloArtist artist1, artist2;
    private GroupArtist group1;
    private Song song1, song2, song3;
    private User user1, user2;
    private Playlist playlist1, playlist2;

    @BeforeEach
    public void setUp() {
        // Create some genres
        rock = new Genre("1", "Rock");
        pop = new Genre("2", "Pop");
        jazz = new Genre("3", "Jazz");

        // Create some artists
        artist1 = new SoloArtist("1", 1990, "John Doe", "USA", "John", "Doe", LocalDate.of(1970, 5, 15));
        artist2 = new SoloArtist("2", 2000, "Jane Smith", "UK", "Jane", "Smith", LocalDate.of(1980, 3, 20));

        group1 = new GroupArtist("3", 2010, "The Rockers", "USA", Arrays.asList(artist1, artist2));

        // Create some songs
        song1 = new Song("1", "Rock Anthem", Duration.ofMinutes(4), artist1, Arrays.asList(rock, jazz));
        song2 = new Song("2", "Pop Hit", Duration.ofMinutes(3), artist2, List.of(pop));
        song3 = new Song("3", "Jazz Vibes", Duration.ofMinutes(5), artist1, List.of(jazz));

        // Create some users
        user1 = new User("1", "user1", new ArrayList<>());
        user2 = new User("2", "user2", new ArrayList<>());

        // Create some playlists
        playlist1 = new Playlist("1", new ArrayList<>(Arrays.asList(song1, song2)), user1, new ArrayList<>(Arrays.asList(user2, user1)));
        playlist2 = new Playlist("2", new ArrayList<>(Arrays.asList(song2, song3)), user2, new ArrayList<>());

        // Add playlists to users
        user1.getPlaylists().add(playlist1);
        user2.getPlaylists().add(playlist2);
    }

    @Test
    public void getTotalLikesTest() {
        assertEquals(2, playlist1.getTotalLikes(), "Le nombre de like pour playlist1 doit être 2");
    }

    @Test
    public void likeUnlikeFunctionalityTest() {
        user1.like(playlist1);
        assertEquals(1, playlist1.getTotalLikes(), "Les nombre total des likes dans playlist2 apres que user1 re-like doit être 1");

        user1.like(playlist2);
        assertEquals(1, playlist2.getTotalLikes(), "Les nombre total des likes dans playlist2 apres que user1 like doit être 1");
    }

    @Test
    public void addToPlaylisttest() {
        playlist1.addToPlaylist(song3);
        assertEquals(3, playlist1.getSongs().size(), "Les chanson dans playlist1 doit être de 3 apres l'ajout de song3");
    }

    @Test
    public void countPlayliststest() {
        long song3PlaylistCount = song3.countPlaylists(Arrays.asList(playlist1, playlist2));
        assertEquals(1, song3PlaylistCount, "Song3 doit être dans 1 playlists");
    }

    @Test
    public void removeByIdTets() {
        System.out.println("Les chansons dans la playlist zvant suppression: " + playlist1.getSongs());
        playlist1.removeById("2");
        System.out.println("Les chansons dans la playlist apres suppresions: " + playlist1.getSongs());
        assertEquals(1, playlist1.getSongs().size(), "Le nombre de chansons dans la playlist après suppression devrait être 1");
    }

    @Test
    public void excludeGenresTest() {
        Playlist filteredPlaylist = playlist1.exclude(List.of(rock));
        assertEquals(1, filteredPlaylist.getSongs().size(), "Les chansons dans le playlist filtré excluant  rock doit être 1");
    }
}

package streams;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SongService {
    private List<Song> songs = new ArrayList<>();

    public void addSong(Song song) {
        songs.add(song);
    }

    public Optional<Song> shortestSong() {
        return songs.stream().sorted(new Comparator<Song>() {
                    @Override
                    public int compare(Song o1, Song o2) {
                        return o1.getLength() - o2.getLength();
                    }
                })
                .findFirst();
    }

    public List<Song> findSongByTitle(String title) {
        return songs.stream()
                .filter(song -> song.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    public boolean isPerformerInSong(Song song, String performer) {
        return song.getPerformers().contains(performer);
    }

    public List<String> titlesBeforeDate(LocalDate date) {
        return songs.stream()
                .filter(song -> song.getRelease().isBefore(date))
                .map(song -> song.getTitle())
                .collect(Collectors.toList());
    }
}

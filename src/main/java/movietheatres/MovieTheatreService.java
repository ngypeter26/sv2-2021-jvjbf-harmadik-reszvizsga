package movietheatres;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {
    private Map<String,List<Movie>> shows = new HashMap<>();
    private Map<String,List<Movie>> allShows = new HashMap<>();


    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path){
        try {
            List<String> lines = Files.readAllLines(path);
            load(lines);
            loadAllShows(lines);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file.", ioe);
        }
    }

    private void load(List<String> lines) {
        List<Movie>  actualMovieList = new ArrayList<>();
        for (String s : lines) {
            String[] temp1 = s.split("-");
            String actualTheatre = temp1[0];
            String[] temp2 = temp1[1].split(";");
            Movie actualMovie = new Movie(temp2[0],LocalTime.parse(temp2[1]));

            if (shows.containsKey(actualTheatre)){
                actualMovieList = shows.get(actualTheatre);
//                shows.remove(actualTheatre);
            }
            actualMovieList.add(actualMovie);

            shows.put(actualTheatre, actualMovieList);
        }
    }

    public Set<String> findMovie(String title){
        Set<String> theatres = new HashSet<>();
        for (String t : allShows.keySet()){
            for (Movie movie : allShows.get(t)){
                if (movie.getTitle().equals(title)){
                    theatres.add(t);
                }
            }
        }
        return theatres;
    }

    public LocalTime findLatestShow(String title){
        if (findMovie(title).size()==0){
            throw new IllegalArgumentException("Movie is not played!");
        }
        LocalTime latestTime = LocalTime.parse("00:00");
        for (String t : allShows.keySet()){
            for (Movie movie : shows.get(t)){
                if (movie.getTitle().equals(title) && movie.getStartTime().isAfter(latestTime)){
                    latestTime = movie.getStartTime();
                }
            }
        }
        return latestTime;
    }


    private void loadAllShows(List<String> lines) {
        for (String s : lines) {
            String[] temp1 = s.split("-");
            String actualTheatre = temp1[0];
            String[] temp2 = temp1[1].split(";");
            Movie actualMovie = new Movie(temp2[0],LocalTime.parse(temp2[1]));

            if (allShows.containsKey(actualTheatre)){
                allShows.get(actualTheatre).add(actualMovie);
            }else{
                List<Movie>  actualMovieList = new ArrayList<>();
                actualMovieList.add(actualMovie);
                allShows.put(actualTheatre, actualMovieList);
            }
        }
    }


}

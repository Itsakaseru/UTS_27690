package id.ac.umn.lancaster.lemuel.uts_27690;

import java.io.Serializable;

public class Song implements Serializable
{
    private String songTitle;
    private String songArtist;
    private String songURI;

    public Song(String songTitle, String songArtist, String songURI) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songURI = songURI;
    }

    public String getSongTitle()   { return this.songTitle; }
    public String getSongArtist() { return this.songArtist; }
    public String getSongURI() { return this.songURI; }

    public void setSongTitle(String songTitle)         { this.songTitle = songTitle; }
    public void setSongDescription(String songArtist)  { this.songArtist = songArtist; }
    public void setVideoURI(String videoURI)           { this.songURI = videoURI; }
}
